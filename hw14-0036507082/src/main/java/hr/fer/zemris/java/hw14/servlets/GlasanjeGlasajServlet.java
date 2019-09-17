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
 * Class GlasanjeGlasajServlet is http servlet. Updates result text file.
 * 
 * @author Filip
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollId = req.getParameter("pollID");
		if (pollId == null)
			return;
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati" + pollId + ".txt");

		if (!Files.exists(Paths.get(fileName))) {
			Files.createFile(Paths.get(fileName));
		}

		String id = req.getParameter("id");
		boolean found = false;
		List<String> res = new ArrayList<String>();
		if (id != null) {
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			for (String line : lines) {
				String[] parts = line.split("\t");
				String numPart = parts[1];
				if (parts[0].equals(id)) {
					found = true;
					int num = Integer.parseInt(numPart);
					num++;
					numPart = Integer.toString(num);
				}
				res.add(parts[0] + "\t" + numPart);
			}
			if (!found) {
				int idPar = Integer.parseInt(id);
				int br = 0;
				for (String line : res) {
					String[] parts = line.split("\t");
					int number = Integer.parseInt(parts[0]);
					if (idPar < number)
						break;
					br++;
				}
				res.add(br, id + "\t" + "1");
			}
			Files.write(Paths.get(fileName), res);
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati?pollID=" + pollId);
	}

}
