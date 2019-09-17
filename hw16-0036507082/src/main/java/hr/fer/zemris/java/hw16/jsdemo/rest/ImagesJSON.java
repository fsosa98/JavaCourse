package hr.fer.zemris.java.hw16.jsdemo.rest;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.jsdemo.servlets.Image;
import hr.fer.zemris.java.hw16.jsdemo.servlets.ImageDB;

/**
 * Class ImagesJSON is rest class.
 * 
 * @author Filip
 */
@Path("/images")
public class ImagesJSON {
	
	@Context
	private ServletContext context;

	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getTags() {
				
		List<String> tags = ImageDB.getListTags();
		tags.sort((a, b) -> a.compareTo(b));

		String[] array = new String[tags.size()];
		tags.toArray(array);

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	@GET
	@Path("{tag}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImages(@PathParam("tag") String tag) throws IOException {
						
		String thumbPath = ImageDB.getPathThumb();
		
		List<Image> images = ImageDB.getImages(tag);

		List<Image> attr = new ArrayList<Image>();

		for (Image img : images) {
			String pathI = img.getPath();
			String pathO = thumbPath + "/" + img.getName();
			Image img2 = new Image(img.getName(), img.getDescription(), img.getTags(), pathO);
			attr.add(img2);
			
			//check if thumbnail exist
			File file1 = new File(pathO);
			BufferedImage img1 = null;
			try {
				img1 = ImageIO.read(file1);
			} catch (IOException exc) {
				img1 = null;
			}
			
			if (img1 == null) {
				File inputFile = new File(pathI);
				BufferedImage inputImage = ImageIO.read(inputFile);
				img1 = new BufferedImage(150, 150, inputImage.getType());

				Graphics2D g2d = img1.createGraphics();
				g2d.drawImage(inputImage, 0, 0, 150, 150, null);
				g2d.dispose();
				ImageIO.write(img1, "jpg", new File(pathO));
			}
		}
		
		Image[] array = new Image[attr.size()];
		attr.toArray(array);

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		return Response.status(Status.OK).entity(jsonText).build();
		
	}
	
	@GET
	@Path("/bigImage/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBigImages(@PathParam("path") String path) throws IOException {
				
		Image image = ImageDB.getImageByName(path);		

		Gson gson = new Gson();
		String jsonText = gson.toJson(image);

		return Response.status(Status.OK).entity(jsonText).build();
		
	}

}
