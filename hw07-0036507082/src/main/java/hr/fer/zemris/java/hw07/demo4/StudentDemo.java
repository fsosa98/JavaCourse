package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class StudentDemo is demo class.
 * 
 * @author Filip
 */
public class StudentDemo {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("studenti.txt"));
		} catch (IOException exc) {
			System.out.println("Invalid path.");
		}
		List<StudentRecord> records = convert(lines);

		System.out.println("Zadatak 1");
		System.out.println("=========");
		System.out.println(vratiBodovaViseOd25(records));

		System.out.println("Zadatak 2");
		System.out.println("=========");
		System.out.println(vratiBrojOdlikasa(records));

		System.out.println("Zadatak 3");
		System.out.println("=========");
		vratiListuOdlikasa(records).forEach(System.out::println);

		System.out.println("Zadatak 4");
		System.out.println("=========");
		vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

		System.out.println("Zadatak 5");
		System.out.println("=========");
		vratiPopisNepolozenih(records).forEach(System.out::println);

		System.out.println("Zadatak 6");
		System.out.println("=========");
		razvrstajStudentePoOcjenama(records).forEach((k, v) -> System.out.println(k + " -> " + v));

		System.out.println("Zadatak 7");
		System.out.println("=========");
		vratiBrojStudenataPoOcjenama(records).forEach((k, v) -> System.out.println(k + " -> " + v));

		System.out.println("Zadatak 8");
		System.out.println("=========");
		razvrstajProlazPad(records).forEach((k, v) -> System.out.println(k + " -> " + v));

	}

	/**
	 * Helper method for parsing lines.
	 * 
	 * @param lines
	 * @return
	 */
	public static List<StudentRecord> convert(List<String> lines) {
		Objects.requireNonNull(lines);
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		for (String line : lines) {
			String[] parts = line.split("\\s+");
			records.add(new StudentRecord(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
					Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
		}
		return records;
	}

	/**
	 * Returns number of students with more than 25 points.
	 * 
	 * @param records
	 * @return
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getpointsMi() + s.getpointsZi() + s.getpointsLab() > 25).count();
	}

	/**
	 * Returns number of students with final grade 5.
	 * 
	 * @param records
	 * @return
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getFinalGrade() == 5).count();
	}

	/**
	 * Returns students with final grade 5.
	 * 
	 * @param records
	 * @return
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getFinalGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns sorted list of students with final grade 5.
	 * 
	 * @param records
	 * @return
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getFinalGrade() == 5)
				.sorted((s2, s1) -> Double.compare(s1.getpointsMi() + s1.getpointsZi() + s1.getpointsLab(),
						s2.getpointsMi() + s2.getpointsZi() + s2.getpointsLab()))
				.collect(Collectors.toList());
	}

	/**
	 * Returns list of students with final grade 1.
	 * 
	 * @param records
	 * @return
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getFinalGrade() == 1)
				.sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag())).map((s) -> s.getJmbag())
				.collect(Collectors.toList());
	}

	/**
	 * Returns map of students grouped by final grade.
	 * 
	 * @param records
	 * @return
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy((s) -> s.getFinalGrade()));
	}

	/**
	 * Returns map of students grouped by final grade.
	 * 
	 * @param records
	 * @return
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getFinalGrade, x -> 1, (s1, s2) -> s1 + s2));
	}

	/**
	 * Returns map of students grouped by final grade(pass or not).
	 * 
	 * @param records
	 * @return
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy((s) -> s.getFinalGrade() != 1));
	}

}
