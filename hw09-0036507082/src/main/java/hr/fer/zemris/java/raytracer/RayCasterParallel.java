package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class RayCasterParallel is parallelized RayCaster.
 * 
 * Users can get IRayTracerProducer.
 * 
 * @author Filip
 */
public class RayCasterParallel {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * This method return IRayTracerProducer.
	 * 
	 * @return IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(width, height, 0, height - 1, cancel, screenCorner, xAxis, yAxis, horizontal,
						vertical, scene, eye, red, green, blue));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Class Job is job class. It is used for parallelization.
	 * 
	 * @author Filip
	 */
	public static class Job extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		int width;
		int height;
		int yMin;
		int yMax;
		static final int treshold = 16;
		AtomicBoolean cancel;
		Point3D screenCorner;
		Point3D xAxis;
		Point3D yAxis;
		double horizontal;
		double vertical;
		Scene scene;
		Point3D eye;
		private short[] red;
		private short[] green;
		private short[] blue;

		/**
		 * Constructor initializes sphere with given parameters.
		 * 
		 * @param width        given width
		 * @param height       given height
		 * @param yMin         given yMin
		 * @param yMax         given yMax
		 * @param cancel       given cancel
		 * @param screenCorner given screenCorner
		 * @param xAxis        given xAxis
		 * @param yAxis        given yAxis
		 * @param horizontal   given horizontal
		 * @param vertical     given vertical
		 * @param scene        given scene
		 * @param eye          given eye
		 * @param red          given red
		 * @param green        given green
		 * @param blue         given blue
		 */
		public Job(int width, int height, int yMin, int yMax, AtomicBoolean cancel, Point3D screenCorner, Point3D xAxis,
				Point3D yAxis, double horizontal, double vertical, Scene scene, Point3D eye, short[] red, short[] green,
				short[] blue) {
			super();
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.cancel = cancel;
			this.screenCorner = screenCorner;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.scene = scene;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		public void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new Job(width, height, yMin, yMin + (yMax - yMin) / 2, cancel, screenCorner, xAxis, yAxis,
							horizontal, vertical, scene, eye, red, green, blue),
					new Job(width, height, yMin + (yMax - yMin) / 2 + 1, yMax, cancel, screenCorner, xAxis, yAxis,
							horizontal, vertical, scene, eye, red, green, blue));
		}

		/**
		 * This is helper method of compute.
		 */
		public void computeDirect() {

			short[] rgb = new short[3];
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
							.sub(yAxis.scalarMultiply((y * vertical) / (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);

					red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
					green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
					blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}

		}
	}

	/**
	 * This is tracer method
	 * 
	 * @param scene given scene
	 * @param ray   given ray
	 * @param rgb   given rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		} else {
			determineColorFor(closest, rgb, scene, ray);
		}
	}

	/**
	 * This is helper method for tracer.
	 * 
	 * @param closest given closest ray intersection
	 * @param rgb     given rgb
	 * @param scene   given scene
	 * @param ray     given ray
	 */
	private static void determineColorFor(RayIntersection closest, short[] rgb, Scene scene, Ray ray) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		for (LightSource ls : scene.getLights()) {
			Ray lightRay = Ray.fromPoints(ls.getPoint(), closest.getPoint());
			RayIntersection S2 = findClosestIntersection(scene, lightRay);
			if (S2 == null
					|| ls.getPoint().sub(S2.getPoint()).norm() + 0.01 < ls.getPoint().sub(closest.getPoint()).norm()) {
				continue;
			}

			double id = ls.getPoint().sub(closest.getPoint()).normalize().scalarProduct(closest.getNormal());
			rgb[0] += closest.getKdr() * ls.getR() * (id > 0 ? id : 0);
			rgb[1] += closest.getKdg() * ls.getG() * (id > 0 ? id : 0);
			rgb[2] += closest.getKdb() * ls.getB() * (id > 0 ? id : 0);

			// 𝑟=𝑑−2(𝑑⋅𝑛)𝑛
			Point3D r = ls.getPoint().sub(closest.getNormal().normalize()
					.scalarMultiply(2 * ls.getPoint().scalarProduct(closest.getNormal().normalize())));
			double cos = r.normalize().scalarProduct(ray.start.sub(closest.getPoint()).normalize());

			if (cos > 0) {
				cos = Math.pow(cos, closest.getKrn());
				rgb[0] += closest.getKrr() * cos * ls.getR();
				rgb[1] += closest.getKrg() * cos * ls.getG();
				rgb[2] += closest.getKrb() * cos * ls.getB();
			}

		}
	}

	/**
	 * This method returns closest intersection of given scene and ray.
	 * 
	 * @param scene given scene
	 * @param ray   given ray
	 * @return RayIntersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection result = null;
		for (GraphicalObject obj : scene.getObjects()) {
			RayIntersection intersetion = obj.findClosestRayIntersection(ray);
			if (intersetion != null && (result == null || intersetion.getDistance() < result.getDistance())) {
				result = intersetion;
			}
		}
		return result;
	}

}
