package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Class GlasanjeGrafikaServlet is http servlet. Draw a pie chart on stream.
 * 
 * @author Filip
 */
@WebServlet(name = "glasgraf", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileName2 = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> bandsDefinition = Files.readAllLines(Paths.get(fileName2));

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart(lines, bandsDefinition);
		int width = 450;
		int height = 300;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * This is helper method for getting chart with given parameters.
	 * 
	 * @param lines
	 * @param bandsDefinition
	 * @return chart
	 */
	public JFreeChart getChart(List<String> lines, List<String> bandsDefinition) {

		DefaultPieDataset dataset = new DefaultPieDataset();
		int n = 0;
		for (String line : lines) {
			String[] parts = line.split("\t");
			n += Integer.parseInt(parts[1]);
		}
		for (String line : lines) {
			String[] parts = line.split("\t");
			String name = "unk";
			for (String band : bandsDefinition) {
				String[] bandParts = band.split("\t");
				if (bandParts[0].equals(parts[0])) {
					name = bandParts[1];
					break;
				}
			}
			dataset.setValue(name, Integer.parseInt(parts[1]) / (double) n);
		}

		JFreeChart chart = ChartFactory.createPieChart("Pie-chart", dataset, true, false, false);
		return chart;
	}

}
