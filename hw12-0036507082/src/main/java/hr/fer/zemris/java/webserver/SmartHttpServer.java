package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class SmartHttpServer is smart http server class.
 * 
 * @author Filip
 */
public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();

	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<String, SmartHttpServer.SessionMapEntry>();
	private Random sessionRandom = new Random();

	/**
	 * Constructor with given file name.
	 * 
	 * @param configFileName
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		InputStream is;
		try {
			is = Files.newInputStream(Paths.get(configFileName));
			properties.load(is);
			address = properties.getProperty("server.address");
			domainName = properties.getProperty("server.domainName");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
			documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

			String mimePath = properties.getProperty("server.mimeConfig");
			String workersPath = properties.getProperty("server.workers");

			List<String> mimeList = null;
			mimeList = Files.readAllLines(Paths.get(mimePath));
			mimeList.forEach((s) -> {
				String[] parts = s.split("=");
				mimeTypes.put(parts[0].trim(), parts[1].trim());
			});

			List<String> workersList = null;
			workersList = Files.readAllLines(Paths.get(workersPath));
			for (String s : workersList) {
				if (s.startsWith("#") || s.isBlank())
					continue;
				String[] parts = s.split("=");
				String path = parts[0].trim();
				String fqcn = parts[1].trim();

				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					workersMap.put(path, iww);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			System.out.println("Invalid server.properties file or path.");
		}
	}

	/**
	 * Start method.
	 */
	protected synchronized void start() {

		Thread sessionThread = new Thread(() -> {
			while (true) {
				for (var entry : sessions.entrySet()) {
					if (entry.getValue().validUntil >= System.currentTimeMillis() / 1000) {
						sessions.remove(entry.getKey());
					}
				}
				try {
					Thread.sleep(300 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		sessionThread.setDaemon(true);
		sessionThread.start();

		if (serverThread == null) {
			serverThread = new ServerThread();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});

		if (!serverThread.isAlive()) {
			serverThread.start();
		}
	}

	/**
	 * Stop method.
	 */
	@SuppressWarnings("deprecation")
	protected synchronized void stop() {
		serverThread.stop();
		threadPool.shutdown();
	}

	/**
	 * Class for server thread.
	 * 
	 * @author Filip
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Class ClientWorker is client worker class.
	 * 
	 * @author Filip
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		@SuppressWarnings("unused")
		private String version;
		@SuppressWarnings("unused")
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> tempParams = new HashMap<String, String>();
		private Map<String, String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		@SuppressWarnings("unused")
		private String SID;
		private RequestContext context;

		/**
		 * Constructor.
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);
				String firstLine = headers.isEmpty() ? null : headers.get(0);

				if (firstLine == null || firstLine.split(" ").length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String[] firstLineParts = firstLine.split(" ");

				String method = firstLineParts[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 400, "Method Not Allowed");
					csocket.close();
					return;
				}

				String version = firstLineParts[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 400, "HTTP Version Not Supported");
					csocket.close();
					return;
				}

				host = domainName;
				headers.forEach((s) -> {
					if (s.substring(0, 5).equals("Host:")) {
						host = s.split(":")[1].trim();
					}
				});
				checkSession(headers);

				String requestedPath = firstLineParts[1];
				if (requestedPath.split("\\?").length == 2) {
					String paramString = requestedPath.split("\\?")[1];
					parseParameters(paramString);
				}

				String urlPath = requestedPath.split("\\?")[0];

				internalDispatchRequest(urlPath, true);

			} catch (IOException e) {
				System.out.println("Error.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * This method check session.
		 * 
		 * @param headers
		 */
		private synchronized void checkSession(List<String> headers) {

			String sidCandidate = null;
			for (String line : headers) {
				if (!line.startsWith("Cookie:"))
					continue;
				String[] parts = line.substring(8).split(";");
				for (String part : parts) {
					if (part.split("=")[0].trim().equals("sid")) {
						sidCandidate = part.split("=")[1].trim();
						sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1);
					}
				}
			}
			if (sidCandidate == null) {
			} else {
				SessionMapEntry sessionEntry = sessions.get(sidCandidate);
				if (sessionEntry != null) {
					if (sessionEntry.host.equals(this.host)) {
						if (System.currentTimeMillis() / 1000 >= sessionEntry.validUntil) {
							sessions.remove(sidCandidate);
						} else {
							sessionEntry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
							permPrams = sessionEntry.map;
							return;
						}
					}
				}
			}
			String sid = "";
			for (int i = 0; i < 20; i++) {
				sid += (char) (sessionRandom.nextInt(26) + 'A');
			}
			long validUntil = (long) System.currentTimeMillis() / 1000 + sessionTimeout;
			SessionMapEntry sessionNew = new SessionMapEntry(sid, host, validUntil,
					new ConcurrentHashMap<String, String>());
			sessions.put(sid, sessionNew);
			permPrams = sessionNew.map;
			outputCookies.add(new RCCookie("sid", sid, null, host, "/"));
		}

		/**
		 * Helper method for parsing parameters.
		 * 
		 * @param paramString
		 */
		private void parseParameters(String paramString) {
			String[] parameters = paramString.split("&");
			for (String parameter : parameters) {
				String[] parts = parameter.split("=");
				params.put(parts[0], parts[1]);
			}
		}

		/**
		 * Method for sending error.
		 * 
		 * @param cos
		 * @param statusCode
		 * @param statusText
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Method for reading request.
		 * 
		 * @param is
		 * @return
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Method for extracting headers.
		 * 
		 * @param requestHeader
		 * @return
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Helper method for internal dispatch request.
		 * 
		 * @param urlPath
		 * @param directCall
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			check();

			if (urlPath.startsWith("/ext/")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers.";
				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn + urlPath.substring(5));
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				csocket.close();
				return;
			}

			if (directCall == true && (urlPath.startsWith("/private/") || urlPath.equals("/private"))) {
				sendError(ostream, 404, "Error");
				csocket.close();
				return;
			}

			if (workersMap.get(urlPath) != null) {
				workersMap.get(urlPath).processRequest(context);
				csocket.close();
				return;
			}

			String path = urlPath.split("\\?")[0];
			Path requestedFile = documentRoot.resolve(path.substring(1));
			requestedFile.normalize();
			if (!requestedFile.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				csocket.close();
				return;
			}
			String fileExtension = null;
			String mimeType = null;
			String[] parts = requestedFile.toString().split("\\.");
			if (parts.length > 0) {
				fileExtension = parts[parts.length - 1];
			}
			if (fileExtension == null) {
				mimeType = "application/octet-stream";
			}
			if (fileExtension != null) {
				mimeType = mimeTypes.get(fileExtension);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
			}
			if (fileExtension.equals("smscr")) {
				String documentBody = null;

				try {
					documentBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
				} catch (IOException e) {
					e.printStackTrace();
				}
				new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
						new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this)).execute();
			} else {
				if (!Files.isReadable(requestedFile)) {
					sendError(ostream, 404, "File not found");
					csocket.close();
					return;
				}
				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				rc.setMimeType(mimeType);
				rc.setStatusCode(200);
				rc.setContentLength(Files.size(requestedFile));
				InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile));
				byte[] buff = new byte[1024];
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					rc.write(buff, 0, r);
				}
			}

			csocket.close();

		}

		/**
		 * Helper check method.
		 */
		private void check() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
		}

	}

	/**
	 * Class SessionMapEntry is session map entry
	 * 
	 * @author Filip
	 */
	private static class SessionMapEntry {

		@SuppressWarnings("unused")
		String sid;
		String host;
		long validUntil;
		Map<String, String> map;

		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * This is main method.
	 * 
	 * @param args given path.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Single argument must be path.");
			return;
		}
		new SmartHttpServer(args[0]).start();
	}

}
