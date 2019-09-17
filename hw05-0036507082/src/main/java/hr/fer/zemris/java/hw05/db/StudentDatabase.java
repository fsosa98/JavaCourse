package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class StudentDatabase defines data structure and a set of methods for working
 * with it.
 * 
 * Users can get students records.
 * 
 * @author Filip
 */
public class StudentDatabase {

	private List<StudentRecord> list;
	private Map<String, StudentRecord> map;

	/**
	 * Constructor. Initialize database with given list.
	 * 
	 * @param lines given list
	 */
	public StudentDatabase(List<String> lines) {
		list = new ArrayList<>();
		map = new HashMap<>();
		for (String line : lines) {
			input(line);
		}
	}

	/**
	 * Helper method for input.
	 * 
	 * @param line
	 */
	private void input(String line) {
		String[] parts = line.split("\t");
		int g;
		if (parts.length != 4) {
			System.out.println("Line is wrong.");
			return;
		}
		try {
			g = Integer.parseInt(parts[3]);
		} catch (NumberFormatException exc) {
			System.out.println("Line is wrong.");
			return;
		}
		if (map.get(parts[0]) != null || (g < 1 || g > 5)) {
			System.out.println("Line is wrong.");
			return;
		}
		StudentRecord record = new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
		list.add(record);
		map.put(parts[0], record);
	}

	/**
	 * Returns student with given jmbag in O(1).
	 * 
	 * @param jmbag
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}

	/**
	 * Returns list of student records which accepts given filter.
	 * 
	 * @param filter given filter
	 * @return list
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temporaryList = new ArrayList<>();
		for (StudentRecord record : list) {
			if (filter.accepts(record)) {
				temporaryList.add(record);
			}
		}
		return temporaryList;
	}

}
