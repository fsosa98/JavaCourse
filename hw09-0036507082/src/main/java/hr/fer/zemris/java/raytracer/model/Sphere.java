package hr.fer.zemris.java.raytracer.model;

/**
 * Class Sphere is implementation of Graphical Object. Sphere contains center
 * point and radius.
 * 
 * Users can find closest ray intersection.
 * 
 * @author Filip
 */
public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 * Constructor initializes sphere with given parameters.
	 * 
	 * @param center given center point
	 * @param radius given radius
	 * @param kdr    given diffuse component of red color
	 * @param kdg    given diffuse component of green color
	 * @param kdb    given diffuse component of blue color
	 * @param krr    given reflective component of red color
	 * @param krg    given reflective component of green color
	 * @param krb    given reflective component of blue color
	 * @param krn    given krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D start = ray.start;
		Point3D direction = ray.direction;

		double a = direction.scalarProduct(direction);
		double b = 2 * direction.scalarProduct(start.sub(center));
		double c = start.sub(center).scalarProduct(start.sub(center)) - radius * radius;
		double det = b * b - 4 * a * c;
		if (det < 0) {
			return null;
		}
		double t0 = (-b + Math.sqrt(det)) / (2 * a);
		double t1 = (-b - Math.sqrt(det)) / (2 * a);
		double t = t0;
		if (t0 < 0) {
			if (t1 < 0) {
				return null;
			}
			t = t1;
		} else {
			if (t1 >= 0 && t1 < t0) {
				t = t1;
			}
		}

		return new RayIntersection(
				new Point3D(start.x + t * direction.x, start.y + t * direction.y, start.z + t * direction.z), t,
				false) {

			@Override
			public Point3D getNormal() {
				return (this.getPoint().sub(center)).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
