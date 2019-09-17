package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class NewtonProducer is implementation of IFractalProducer. It is used for
 * displaying fractals derived from Newton-Raphson.
 * 
 * @author Filip
 */
public class NewtonProducer implements IFractalProducer {

	private ComplexPolynomial polynomial;
	private ComplexRootedPolynomial rootedPolynomial;
	private ExecutorService pool;

	private final static int NUMBER_OF_ITERATIONS = 16 * 16;
	private final static double THRESHOLD = 1E-3;
	private final static double ROOT_DISTANCE = 0.002;

	/**
	 * Constructor initializes producer with given polynomial.
	 * 
	 * @param rootedPolynomial
	 */
	public NewtonProducer(ComplexRootedPolynomial rootedPolynomial) {
		this.rootedPolynomial = rootedPolynomial;
		this.polynomial = rootedPolynomial.toComplexPolynom();
		this.pool = Executors.newFixedThreadPool(8 * Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		short[] data = new short[width * height];
		int tracks = 16;
		int ytracks = height / tracks;
		List<Future<Void>> list = new ArrayList<Future<Void>>();
		
		for(int i = 0; i < tracks; i++) {
			int yMin = i*ytracks;
			int yMax = (i+1)*ytracks-1;
			if(i==tracks-1) {
				yMax = height-1;
			}
			Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
			list.add(pool.submit(job));
		}
		for(Future<Void> jobFuture : list) {
			try {
				jobFuture.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}

	/**
	 * Class Job is job class. It is used for parallelized of calculating fractals.
	 * 
	 * 
	 * @author Filip
	 */
	private class Job implements Callable<Void> {

		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int width;
		private int height;
		private int yMin;
		private int yMax;
		private short[] data;
		@SuppressWarnings("unused")
		private AtomicBoolean cancel;

		/**
		 * Constructor initializes job with given parameters.
		 * 
		 * @param reMin  minimal real part
		 * @param reMax  maximal real part
		 * @param imMin  minimal imaginary part
		 * @param imMax  maximal imaginary part
		 * @param width  given width
		 * @param height given height
		 * @param yMin   minimal y
		 * @param yMax   maximal y
		 * @param data   given data
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				short[] data, AtomicBoolean cancel) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() throws Exception {

			int offset = yMin * width;

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Complex c = map_to_complex_plain(x, y);
					Complex zn = c;
					int iter = 0;
					double module;
					do {

						Complex numerator = polynomial.apply(zn);
						Complex denominator = polynomial.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;

					} while (module > THRESHOLD && iter < NUMBER_OF_ITERATIONS);
					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_DISTANCE);

					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index + 1);
					}
				}

			}

			return null;
		}

		/**
		 * This method calculates complex number from given x and y.
		 * 
		 * @param x given x
		 * @param y given y
		 * @return complex number
		 */
		private Complex map_to_complex_plain(int x, int y) {
			return new Complex(((double) x / (width - 1)) * (reMax - reMin) + reMin,
					((height - 1 - (double) y) / (width - 1)) * (imMax - imMin) + imMin);
		}

	}

}
