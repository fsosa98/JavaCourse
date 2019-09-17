package hr.fer.zemris.java.hw17.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Class Konzola is console class. Users can write queries. Users also can
 * recall results and get document text from result.
 * 
 * @author Filip
 */
public class Konzola {

	private static Set<String> vocabulary;
	private static List<String> stopping;
	private static int NUMBER_OF_DOCUMENTS;
	private static Map<String, Double> idf = new HashMap<String, Double>();
	private static List<Document> documents = new ArrayList<Konzola.Document>();

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			System.exit(1);
		}

		String path = args[0];
		if (!Files.exists(Paths.get(path)) || !Files.isDirectory(Paths.get(path))) {
			System.out.println("Wrong path.");
			System.exit(1);
		}

		try {
			stopping = Files.readAllLines(Paths.get("zaustavne.txt"));
			getVocabulary(path);
			idfFill(path);
		} catch (IOException e) {
			System.out.println("Error.");
			System.exit(1);
		}

		System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");
		System.out.println();

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter command > ");
		String input = sc.nextLine();
		input = input.trim();
		@SuppressWarnings("unused")
		boolean res = false;
		List<Reuslt> results = new ArrayList<Konzola.Reuslt>();
		while (!input.equals("exit")) {
			if (input.startsWith("query")) {
				String[] query = input.substring(5).trim().split("\\s+");
				List<String> queryList = new ArrayList<String>();
				System.out.print("Query is: [");
				for (int i = 0; i < query.length; i++) {
					if (vocabulary.contains(query[i])) {
						queryList.add(query[i]);
					}
				}
				for (int i = 0; i < queryList.size(); i++) {
					if (i == 0) {
						System.out.print(queryList.get(i));
					} else {
						System.out.print(", " + queryList.get(i));
					}
				}
				System.out.println("]");
				Vector vi = getVector(queryList);
				results = new ArrayList<Konzola.Reuslt>();
				for (Document doc : documents) {
					Vector vj = doc.getTfidf();
					double dot = Vector.dot(vi, vj);
					results.add(new Reuslt(doc.getPath(), dot / (vi.mag() * vj.mag())));
				}
				List<Reuslt> newRes = new ArrayList<Konzola.Reuslt>();
				for (int j = 0; j < results.size(); j++) {
					if (Math.abs(results.get(j).value) > .0001) {
						newRes.add(results.get(j));
					}
				}
				results = newRes;
				results.sort((r1, r2) -> Double.compare(r2.value, r1.value));
				res = true;
				System.out.println("Najboljih 10 rezultata:");
				printResults(results);
			} else if (input.startsWith("type")) {
				if (res = false) {
					System.out.println("Izvršite query prvo.");
				} else {
					String[] parts = input.split("\\s+");
					if (parts.length != 2) {
						System.out.println("Type ima jedan argument");
					} else {
						try {
							int x = Integer.parseInt(parts[1]);
							if (x >= results.size() || x < 0) {
								System.out.println("Neispravan argument");
							} else {
								printDocument(results.get(x));
							}
						} catch (NumberFormatException exc) {
							System.out.println("Nepoznata naredba.");
						}
					}
				}
			} else if (input.equals("results")) {
				if (res = false) {
					System.out.println("Izvršite query prvo.");
				} else {
					printResults(results);
				}
			} else {
				System.out.println("Nepoznata naredba.");
			}
			System.out.print("Enter command > ");
			input = sc.nextLine().trim();
		}

		sc.close();

	}

	/**
	 * Helper method for printing document text.
	 * 
	 * @param reuslt
	 */
	private static void printDocument(Reuslt reuslt) {
		System.out.println("----------------------------------------------------------------");
		System.out.println("Dokument: " + reuslt.path);
		System.out.println("----------------------------------------------------------------");
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(reuslt.path));
			for (String line : lines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Greška");
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println();
	}

	/**
	 * Helper method for printing results.
	 * 
	 * @param results
	 */
	private static void printResults(List<Reuslt> results) {
		int br = 0;
		for (Reuslt r : results) {
			System.out.printf("[%2d] (", br);
			System.out.printf("%.4f", r.value);
			System.out.println(") " + r.path);
			br++;
		}
	}

	/**
	 * Getter for vector of given query.
	 * 
	 * @param query
	 * @return
	 */
	private static Vector getVector(List<String> query) {
		Vector v = new Vector();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String q : query) {
			Integer i = map.get(q);
			if (i == null) {
				map.put(q, 1);
			} else {
				map.put(q, i + 1);
			}
		}
		for (String word : vocabulary) {
			Integer i = map.get(word);
			if (i == null) {
				i = 0;
			}
			v.addValue(i * idf.get(word));
		}
		return v;
	}

	/**
	 * Initialization method for filling idf vector.
	 * 
	 * @param path
	 * @throws IOException
	 */
	private static void idfFill(String path) throws IOException {

		for (String word : vocabulary) {
			int br = 0;
			for (Document doc : documents) {
				if (doc.contains(word)) {
					br++;
				}
			}
			double value = Math.log(NUMBER_OF_DOCUMENTS / br);
			idf.put(word, value);
			for (Document doc : documents) {
				doc.getTfidf().addValue(doc.getTf(word) * value);
			}
		}

	}

	/**
	 * Initialization method for making vocabulary.
	 * 
	 * @param path
	 * @throws IOException
	 */
	private static void getVocabulary(String path) throws IOException {
		vocabulary = new HashSet<String>();

		File folder = new File(path);
		File[] files = folder.listFiles();
		NUMBER_OF_DOCUMENTS = files.length;
		for (File file : files) {
			Document doc = new Document(file.getName(), file.toPath().toString());
			documents.add(doc);
			if (file.isFile()) {
				List<String> lines = Files.readAllLines(file.toPath());
				for (String line : lines) {
					addLine(line, doc);
				}
			}
		}

	}

	/**
	 * Helper method for adding line.
	 * 
	 * @param line
	 * @param doc
	 */
	private static void addLine(String line, Document doc) {
		line = line.trim();
		char[] data = line.toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			if (Character.isAlphabetic(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				continue;
			} else {
				if (sb.length() != 0) {
					String word = sb.toString().toLowerCase();
					if (!stopping.contains(word)) {
						doc.put(word);
						vocabulary.add(word);
					}
					sb = new StringBuilder();
				}
				currentIndex++;
			}
		}
		if (sb.length() != 0) {
			String word = sb.toString().toLowerCase();
			if (!stopping.contains(word)) {
				doc.put(word);
				vocabulary.add(word);
			}
		}
	}

	/**
	 * Class Reuslt is result class.
	 * 
	 * @author Filip
	 */
	private static class Reuslt {
		private String path;
		private double value;

		public Reuslt(String path, double value) {
			this.path = path;
			this.value = value;
		}

	}

	/**
	 * Class Document is document class.
	 * 
	 * @author Filip
	 */
	private static class Document {

		@SuppressWarnings("unused")
		private String name;
		private String path;
		private Map<String, Integer> map = new HashMap<String, Integer>();
		Vector tfidf = new Vector();

		/**
		 * Constructor with given parameters.
		 * 
		 * @param name
		 * @param path
		 */
		public Document(String name, String path) {
			this.name = name;
			this.path = path;
		}

		/**
		 * Checks if document contains word.
		 * 
		 * @param word
		 * @return
		 */
		public boolean contains(String word) {
			return map.containsKey(word);
		}

		/**
		 * Putting word in map.
		 * 
		 * @param word
		 */
		public void put(String word) {
			Integer i = map.get(word);
			if (i == null) {
				i = 1;
			} else {
				i++;
			}
			map.put(word, i);
		}

		/**
		 * Getter for tf
		 * 
		 * @param word
		 * @return
		 */
		public Integer getTf(String word) {
			Integer i = map.get(word);
			if (i == null) {
				return 0;
			}
			return i;
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
		 * Getter for tfidf.
		 * 
		 * @return
		 */
		public Vector getTfidf() {
			return tfidf;
		}

	}

}
