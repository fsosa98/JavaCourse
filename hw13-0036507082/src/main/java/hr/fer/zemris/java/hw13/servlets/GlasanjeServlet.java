package hr.fer.zemris.java.hw13.servlets;

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
 * Class GlasanjeServlet is http servlet. Sets information about bands.
 * 
 * @author Filip
 */
@WebServlet(name = "glas", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<BandValues> bands = new ArrayList<GlasanjeServlet.BandValues>();
		for (String line : lines) {
			BandValues band = parseBand(line);
			bands.add(band);
		}

		bands.sort((a, b) -> ((Integer) a.getId()).compareTo(b.getId()));

		req.setAttribute("bands", bands);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Helper method for parsing line
	 * 
	 * @param line
	 * @return
	 */
	private BandValues parseBand(String line) {
		String parts[] = line.split("\t");
		return new BandValues(Integer.parseInt(parts[0]), parts[1], parts[2]);
	}

	/**
	 * Class BandValues defines data structure of band values.
	 * 
	 * @author Filip
	 */
	public static class BandValues {

		int id;
		String name;
		String link;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param id
		 * @param name
		 * @param link
		 */
		public BandValues(int id, String name, String link) {
			this.id = id;
			this.name = name;
			this.link = link;
		}

		/**
		 * Getter for id
		 * 
		 * @return id
		 */
		public int getId() {
			return id;
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

}
