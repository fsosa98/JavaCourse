package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

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
@WebServlet(name = "img", urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart();
		int width = 450;
		int height = 300;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * This is helper method for getting chart with given parameters.
	 * 
	 * @return chart
	 */
	public JFreeChart getChart() {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 70);
		dataset.setValue("Windows", 25);
		dataset.setValue("Mac", 5);

		JFreeChart chart = ChartFactory.createPieChart("OS", dataset, true, false, false);
		return chart;
	}

}
