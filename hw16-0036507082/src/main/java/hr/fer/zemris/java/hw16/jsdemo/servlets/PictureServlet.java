package hr.fer.zemris.java.hw16.jsdemo.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class PictureServlet is http servlet.
 * 
 * @author Filip
 */
@WebServlet("/picture")
public class PictureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getParameter("path");

		File file1 = new File(path);
		BufferedImage img1 = ImageIO.read(file1);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ImageIO.write(img1, "jpg", stream);
		resp.getOutputStream().write(stream.toByteArray());
	}

}
