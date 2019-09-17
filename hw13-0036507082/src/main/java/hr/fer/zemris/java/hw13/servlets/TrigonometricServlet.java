package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class TrigonometricServlet is http servlet. Sets trigonometric parameter.
 * 
 * @author Filip
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");

		int numA = 0;
		int numB = 360;
		try {
			if (a != null) {
				numA = Integer.parseInt(a);
			}
		} catch (NumberFormatException exc) {
		}

		try {
			if (b != null) {
				numB = Integer.parseInt(b);
			}
		} catch (NumberFormatException exc) {
		}

		if (numA > numB) {
			int tmp = numA;
			numA = numB;
			numB = tmp;
		}

		if (numB > numA + 720) {
			numB = numA + 720;
		}

		List<TrigValues> list = new ArrayList<TrigonometricServlet.TrigValues>();
		for (int i = numA; i <= numB; i++) {
			list.add(new TrigValues(i));
		}
		req.setAttribute("trigValues", list);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Class TrigValues defines data structure of trigonometric values.
	 * 
	 * @author Filip
	 */
	public static class TrigValues {
		int x;
		double radX;
		double cos;
		double sin;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param x
		 */
		public TrigValues(int x) {
			this.x = x;
			radX = x * (Math.PI / 180);
			this.cos = Math.cos(radX);
			this.sin = Math.sin(radX);
		}

		/**
		 * Getter for x
		 * 
		 * @return x
		 */
		public int getX() {
			return x;
		}

		/**
		 * Getter for cos
		 * 
		 * @return cos
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * Getter for sin
		 * 
		 * @return sin
		 */
		public double getSin() {
			return sin;
		}

	}

}
