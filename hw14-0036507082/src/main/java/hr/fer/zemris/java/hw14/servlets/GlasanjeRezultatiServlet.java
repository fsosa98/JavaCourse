package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class GlasanjeRezultatiServlet is http servlet. Sets result and winner
 * parameters.
 * 
 * @author Filip
 */
@WebServlet(name = "glasrez", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollId = req.getParameter("pollID");
		if (pollId == null)
			return;
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati" + pollId + ".txt");
		String fileName2 = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija" + pollId + ".txt");

		List<String> bandsDefinition = Files.readAllLines(Paths.get(fileName2));

		if (!Files.exists(Paths.get(fileName))) {
			Files.createFile(Paths.get(fileName));
		}

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<ResultValues> results = new ArrayList<GlasanjeRezultatiServlet.ResultValues>();
		for (String line : lines) {
			String[] parts = line.split("\t");
			String name = "Unk";
			String link = null;
			int num = Integer.parseInt(parts[1]);
			for (String band : bandsDefinition) {
				String[] bandParts = band.split("\t");
				if (bandParts[0].equals(parts[0])) {
					name = bandParts[1];
					link = bandParts[2];
					break;
				}
			}
			results.add(new ResultValues(name, num, link));
		}

		results.sort((b, a) -> ((Integer) a.getNumber()).compareTo(b.getNumber()));

		List<Winner> winners = new ArrayList<GlasanjeRezultatiServlet.Winner>();
		for (ResultValues result : results) {
			if (result.getNumber() == results.get(0).getNumber()) {
				winners.add(new Winner(result.getName(), result.getLink()));
				continue;
			}
			break;
		}

		req.setAttribute("result", results);
		req.setAttribute("winners", winners);
		req.setAttribute("idPoll", pollId);

		req.getRequestDispatcher("/WEB-INF/pages/rezultati.jsp").forward(req, resp);
	}

	/**
	 * Class Winner defines data structure of winner.
	 * 
	 * @author Filip
	 */
	public static class Winner {
		String name;
		String link;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param name
		 * @param link
		 */
		public Winner(String name, String link) {
			this.name = name;
			this.link = link;
		}

		/**
		 * Getter for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for link
		 * 
		 * @return link
		 */
		public String getLink() {
			return link;
		}

	}

	/**
	 * Class ResultValues defines data structure of result values.
	 * 
	 * @author Filip
	 */
	public static class ResultValues {
		String name;
		int number;
		String link;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param name
		 * @param number
		 * @param link
		 */
		public ResultValues(String name, int number, String link) {
			this.name = name;
			this.number = number;
			this.link = link;
		}

		/**
		 * Getter for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for number
		 * 
		 * @return number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Getter for link
		 * 
		 * @return link
		 */
		public String getLink() {
			return link;
		}

	}
}
