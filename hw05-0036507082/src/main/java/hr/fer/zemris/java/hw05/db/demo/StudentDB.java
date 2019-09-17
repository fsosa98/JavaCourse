package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class StudentDB for testing purpose.
 *
 * @author Filip
 */
public class StudentDB {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		List<String> lines = null;;
		
		try {
			lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		}catch(IOException e) {
			System.out.println("Wrong path.");
			System.exit(1);
		}
		
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		String input;
		int maxLastName = 0;
		int maxName = 0;
		
		try (Scanner scanner = new Scanner(System.in)){
			while (true) {
				System.out.print("> ");
				input = scanner.nextLine().trim();
				if (input.equals("exit")) {
					break;
				}
				String[] parts = input.split("\\s+");
				if (!parts[0].equals("query")) {
					System.out.println("Wrong input.");
					continue;
				}
				StringBuilder sb = new StringBuilder();
				int i = 1;
				for (; i < parts.length; i ++) {
					sb.append(parts[i]).append(" ");
				}
				QueryParser qp = new QueryParser(new String(sb));
				if (qp.isDirectQuery()) {
					System.out.println("Using index for record retrieval.");
				}
				QueryFilter qf = new QueryFilter(qp.getQuery());
				List<StudentRecord> records = studentDatabase.filter(qf);
				if (records.size() != 0) {
					for (StudentRecord record : records) {
						if (record.getFirstName().length() > maxName) {
							maxName = record.getFirstName().length();
						}
						if (record.getLastName().length() > maxLastName) {
							maxLastName = record.getLastName().length();
						}
					}
					startEnd(maxName, maxLastName);
					for (StudentRecord record : records) {
						System.out.print("| " + record.getJmbag() + " | " + record.getLastName());
						for (int j = record.getLastName().length(); j < maxLastName; j++) {
							System.out.print(" ");
						}
						System.out.print(" | " + record.getFirstName());
						for (int j = record.getFirstName().length(); j < maxName; j++) {
							System.out.print(" ");
						}
						System.out.print(" | " + record.getFinalGrade() + " |\n");
					}
					startEnd(maxName, maxLastName);
				}
				
				System.out.println("Records selected: " + records.size());
				
			}
		}
		System.out.println("Goodbye!");
	}
	
	 
	
	/**
	 * Helper method.
	 * 
	 * @param maxName
	 * @param maxLastName
	 */
	public static void startEnd(int maxName, int maxLastName) {
		System.out.print("+============+");
		for (int j = 0; j < maxLastName+2; j ++) {
			System.out.print("=");
		}
		System.out.print("+");
		for (int j = 0; j < maxName+2; j ++) {
			System.out.print("=");
		}
		System.out.print("+===+\n");
	}
	
}
