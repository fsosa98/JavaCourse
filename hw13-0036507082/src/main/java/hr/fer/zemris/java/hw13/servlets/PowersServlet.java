package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class PowersServlet is http servlet. Sets xls table with numbers and powers
 * of numbers.
 * 
 * @author Filip
 */
@WebServlet(name = "pow", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");

		boolean parOk = true;
		int numA;
		int numB;
		int numN;
		try {
			numA = Integer.parseInt(a);
			numB = Integer.parseInt(b);
			numN = Integer.parseInt(n);
			if (numA > numB) {
				int tmp = numA;
				numA = numB;
				numB = tmp;
			}
			if ((numA < -100 || numA > 100) || (numB < -100 || numB > 100) || (numN < 1 || numN > 5)) {
				parOk = false;
			} else {
				HSSFWorkbook hwb = new HSSFWorkbook();

				for (int i = 0; i < numN; i++) {
					HSSFSheet sheet = hwb.createSheet("sheet" + (i + 1));

					HSSFRow rowhead = sheet.createRow((short) 0);
					rowhead.createCell((short) 0).setCellValue("Number");
					rowhead.createCell((short) 1).setCellValue((i + 1) + "-th power");
					int br = 1;
					for (int j = numA; j <= numB; j++) {
						HSSFRow row = sheet.createRow((short) br++);
						row.createCell((short) 0).setCellValue(j);
						row.createCell((short) 1).setCellValue(Math.pow(j, (i + 1)));
					}
				}

				resp.setContentType("application/vnd.ms-excel");

				OutputStream fileOut = resp.getOutputStream();
				hwb.write(fileOut);
				fileOut.close();
			}
		} catch (NumberFormatException exc) {
			parOk = false;
		}

		if (!parOk) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}

	}

}
