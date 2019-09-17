package hr.fer.zemris.java.hw16.jsdemo.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class ImageDB defines database class.
 * 
 * @author Filip
 */
public class ImageDB {

	private static List<Image> images = new ArrayList<Image>();
	private static Set<String> tags = new HashSet<String>();
	private static String pathThumb;

	/**
	 * Initialization method
	 * 
	 * @param path1
	 * @param path2
	 */
	public static void initialization(String path1, String path2) {
		List<String> lines = null;

		pathThumb = path2.substring(0, path2.lastIndexOf("/")) + "/thumbnails";				

		try {
			if (!Files.exists(Paths.get(pathThumb))) {
				Files.createDirectories(Paths.get(pathThumb));
			}
			lines = Files.readAllLines(Paths.get(path1));
		} catch (IOException e) {
			System.out.println("Error.");
			System.exit(0);
		}

		for (int i = 0; i < lines.size(); i += 3) {
			String name = lines.get(i);
			String desc = lines.get(i + 1);
			String taggs = lines.get(i + 2);
			String[] parts = taggs.split(",");
			String[] imTags = new String[parts.length];
			int br = 0;
			for (String tag : parts) {
				tags.add(tag.trim());
				imTags[br] = tag.trim();
				br++;
			}
			Image image = new Image(name, desc, imTags, path2 + "/" + name);
			images.add(image);
		}
	}

	/**
	 * Getter for pathThumb
	 * 
	 * @return
	 */
	public static String getPathThumb() {
		return pathThumb;
	}

	/**
	 * Getter for image with given name
	 * 
	 * @param name
	 * @return
	 */
	public static Image getImageByName(String name) {
		for (Image image : images) {
			if (name.equals(image.getName())) {
				return image;
			}
		}
		return null;
	}

	/**
	 * Getter for tags
	 * 
	 * @return
	 */
	public static List<String> getListTags() {
		return new ArrayList<String>(tags);
	}

	/**
	 * Getter for images with given tag
	 * 
	 * @param tag
	 * @return
	 */
	public static List<Image> getImages(String tag) {
		List<Image> imgs = new ArrayList<Image>();

		for (Image image : images) {
			String[] tags = image.getTags();
			for (String t : tags) {
				if (t.equals(tag)) {
					imgs.add(image);
					break;
				}
			}
		}

		return imgs;
	}

}
