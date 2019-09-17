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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class GlasanjeXlsServlet is http servlet. Sets xls table with result
 * information in table.
 * 
 * @author Filip
 */
@WebServlet(name = "xls", urlPatterns = { "/glasanje-xls" })
public class GlasanjeXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileName2 = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> bandsDefinition = Files.readAllLines(Paths.get(fileName2));

		HSSFSheet sheet = hwb.createSheet("sheet");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Bend");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		int br = 1;
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
			HSSFRow row = sheet.createRow((short) br++);
			row.createCell((short) 0).setCellValue(name);
			row.createCell((short) 1).setCellValue(parts[1]);
		}

		resp.setContentType("application/vnd.ms-excel");

		OutputStream fileOut = resp.getOutputStream();
		hwb.write(fileOut);
		fileOut.close();
	}

}
