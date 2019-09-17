package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class RequestContext defines data structure and a set of methods for working
 * with it.
 * 
 * Users can get and set parameters, temporary parameters and persistent
 * parameters
 * 
 * @author Filip
 */
public class RequestContext {

	private OutputStream outputStream;
	private Charset charset;
	private String encoding = "UTF-8";
	private int statusCode = 200;
	private String statusText = "OK";
	private String mimeType = "text/html";
	private Long contentLength = null;
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters = new HashMap<String, String>();
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated = false;
	private IDispatcher dispatcher;
	private String sessionID;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 * @param sessionID
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sessionID) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sessionID = sessionID;
	}

	/**
	 * Constructor with given parameters.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Constructor with given parameters.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream, "Output stream is null.");
		this.outputStream = outputStream;

		if (parameters == null) {
			this.parameters = new HashMap<String, String>();
		} else {
			this.parameters = new HashMap<String, String>(parameters);
		}

		this.persistentParameters = Objects.requireNonNullElseGet(persistentParameters, HashMap::new);

		if (outputCookies == null) {
			this.outputCookies = new ArrayList<RCCookie>();
		} else {
			this.outputCookies = new ArrayList<RCCookie>(outputCookies);
		}

	}

	/**
	 * Getter for dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * This method adds cookie.
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}

	/**
	 * Setter for encoding.
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.encoding = encoding;
	}

	/**
	 * Setter for status code.
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text.
	 * 
	 * @param statusText
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.mimeType = mimeType;
	}

	/**
	 * Setter for length.
	 * 
	 * @param contentLength
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.contentLength = contentLength;
	}

	/**
	 * Setter for outputCookies.
	 * 
	 * @param outputCookies
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if (headerGenerated)
			throw new RuntimeException("Header created.");
		this.outputCookies = outputCookies;
	}

	/**
	 * Getter for temporaryParameters
	 * 
	 * @return temporaryParameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Setter for temporaryParameters
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Getter for persistentParameters
	 * 
	 * @return persistentParameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Setter for persistentParameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Getter for parameters
	 * 
	 * @return parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Getter for parameter with given name.
	 * 
	 * @param name given name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Getter for parameter names.
	 * 
	 * @return
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Getter for persistentParameters with given name.
	 * 
	 * @param name
	 * @return
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Getter for persistentParameters.
	 * 
	 * @return
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Setter for persistentParameters with given name.
	 * 
	 * @param name
	 * @param value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * This method removes persistentParameters with given name.
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Getter for temporaryParameters with given name.
	 * 
	 * @param name
	 * @return
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Getter for temporaryParameters.
	 * 
	 * @return
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Getter for sessionID.
	 * 
	 * @return
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Setter for temporaryParameters with given parameters.
	 * 
	 * @param name
	 * @param value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * This method removes temporaryParameters with given name.
	 * 
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * This method writes byte data.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated)
			headerCreate();
		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * This method writes byte data with given offset and length.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated)
			headerCreate();
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}

	/**
	 * This method writes given text.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated)
			headerCreate();
		outputStream.write(text.getBytes(charset));
		outputStream.flush();
		return this;
	}

	/**
	 * Helper method for creating header.
	 * 
	 * @throws IOException
	 */
	private void headerCreate() throws IOException {
		this.charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.substring(0, 5).equals("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		if (outputCookies != null) {
			for (RCCookie cookie : outputCookies) {
				// 'Set-Cookie: ' name '=”' value '”; Domain=' domain '; Path=' path ';
				// Max-Age=' maxAge
				sb.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"");
				if (cookie.getDomain() != null) {
					sb.append("; Domain=" + cookie.getDomain());
				}
				if (cookie.getPath() != null) {
					sb.append("; Path=" + cookie.getPath());
				}
				if (cookie.getMaxAge() != null) {
					sb.append("; Max-Age=" + cookie.getMaxAge());
				}
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}

	/**
	 * Class RCCookie is cookie class.
	 * 
	 * @author Filip
	 */
	public static class RCCookie {

		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter for name.
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value.
		 * 
		 * @return
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain.
		 * 
		 * @return
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path.
		 * 
		 * @return
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for maxAge.
		 * 
		 * @return
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}

}
