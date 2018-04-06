package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;
import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Bounds;
import de.ralfhergert.math.geom.Mesh;
import de.ralfhergert.math.geom.Vertex;
import de.ralfhergert.math.geom.VertexLocation;
import org.alltiny.math.vector.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This initializer fill the outline of the simulation with particles.
 */
public class FillOutlineWithParticles implements Initializer {

	private static final Logger LOG = LoggerFactory.getLogger(FillOutlineWithParticles.class);

	private final int numberOfParticles;

	private final double density;

	private final double specificGasConstant;

	private final double temperature;

	private Vector velocity = new Vector(3);

	public FillOutlineWithParticles(int numberOfParticles, double density, double specificGasConstant, double temperature) {
		if (numberOfParticles <= 0) {
			throw new IllegalArgumentException("number of particles must be greater than 0");
		}
		if (density < 0) {
			throw new IllegalArgumentException("density must be greater than or equal 0");
		}
		if (specificGasConstant <= 0) {
			throw new IllegalArgumentException("specificGasConstant must be greater than 0");
		}
		if (temperature < 0) {
			throw new IllegalArgumentException("temperature must be greater than or equal 0");
		}
		this.numberOfParticles = numberOfParticles;
		this.density = density;
		this.specificGasConstant = specificGasConstant;
		this.temperature = temperature;
	}

	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	public double getDensity() {
		return density;
	}

	public double getTemperature() {
		return temperature;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	@Override
	public Result applyTo(Simulation simulation) {
		if (simulation == null) {
			return new NoSimulationGiven();
		}
		final Mesh outline = simulation.getOutline();
		if (outline == null) {
			return new NoSimulationOutlineGiven();
		}
		if (!outline.isImpermeable()) {
			return new SimulationOutlinePermeable();
		}
		final double massPerParticle = density * outline.calcVolume() / numberOfParticles;
		final Bounds bounds = outline.getBounds();
		final Frame frame;
		List<Frame> frames = simulation.getFrames();
		if (frames.isEmpty()) {
			LOG.info("simulation has not yet a frame: creating frame(0)");
			frame = new Frame(0);
			simulation.appendFrame(frame);
		} else {
			frame = frames.get(frames.size() - 1);
		}
		while (frame.getParticles().size() < numberOfParticles) {
			Vertex vertex = new Vertex(createRandomVectorInBounds(bounds));
			if (outline.calcVertexLocation(vertex) != VertexLocation.InBounds) {
				continue;
			}
			frame.addParticle(new Particle(massPerParticle, specificGasConstant, temperature)
				.setPosition(vertex));
		}
		return new Result(false, "Done");
	}

	/**
	 * This method creates a vector within the given bounds.
	 */
	public static Vector createRandomVectorInBounds(Bounds bounds) {
		if (bounds == null) {
			return null;
		}
		double values[] = new double[bounds.getDimension()];
		for (int i = 0; i < values.length; i++) {
			final double min = bounds.getMin().get(i);
			values[i] = min + Math.random() * (bounds.getMax().get(i) - min);
		}
		return new Vector(values);
	}

	/**
	 * This error result is given when simulation was null.
	 */
	class NoSimulationGiven extends Result {
		public NoSimulationGiven() {
			super(true, "No simulation has been given");
		}
	}

	/**
	 * This error result is given when the simulation has no outline.
	 */
	class NoSimulationOutlineGiven extends Result {
		public NoSimulationOutlineGiven() {
			super(true, "Simulation has no outline");
		}
	}

	/**
	 * This error result is given when the simulation outline is incomplete
	 * and therefor not impermeable.
	 */
	class SimulationOutlinePermeable extends Result {
		public SimulationOutlinePermeable() {
			super(true, "Simulation outline is incomplete (not a closed mesh)");
		}
	}
}
