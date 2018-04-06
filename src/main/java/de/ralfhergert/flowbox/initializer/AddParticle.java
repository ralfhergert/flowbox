package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;
import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This initializer fill the outline of the simulation with particles.
 */
public class AddParticle implements Initializer {

	private static final Logger LOG = LoggerFactory.getLogger(AddParticle.class);

	private final double mass;
	private final double specificGasConstant;
	private final double temperature;

	private Vertex position = new Vertex(new Vector(3));
	private Vector velocity = new Vector(3);

	public AddParticle(double mass, double specificGasConstant, double temperature) {
		if (mass <= 0) {
			throw new IllegalArgumentException("mass must be greater than 0");
		}
		if (specificGasConstant <= 0) {
			throw new IllegalArgumentException("specificGasConstant must be greater than 0");
		}
		if (temperature < 0) {
			throw new IllegalArgumentException("temperature must be greater than or equal 0");
		}
		this.mass = mass;
		this.specificGasConstant = specificGasConstant;
		this.temperature = temperature;
	}

	public double getMass() {
		return mass;
	}

	public double getSpecificGasConstant() {
		return specificGasConstant;
	}

	public double getTemperature() {
		return temperature;
	}

	public Vertex getPosition() {
		return position;
	}

	public AddParticle setPosition(Vertex position) {
		this.position = position;
		return this;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public AddParticle setVelocity(Vector velocity) {
		this.velocity = velocity;
		return this;
	}

	@Override
	public Result applyTo(Simulation simulation) {
		if (simulation == null) {
			return new NoSimulationGiven();
		}
		final Frame frame;
		List<Frame> frames = simulation.getFrames();
		if (frames.isEmpty()) {
			LOG.info("simulation has not yet a frame: creating frame(0)");
			frame = new Frame(0);
			simulation.appendFrame(frame);
		} else {
			frame = frames.get(frames.size() - 1);
		}
		Particle particle = new Particle(mass, specificGasConstant, temperature)
			.setPosition(position)
			.setVelocity(velocity);
		LOG.debug("adding particle {} to frame {}", particle, frame);
		frame.addParticle(particle);
		return new Result(false, "Done");
	}

	/**
	 * This error result is given when simulation was null.
	 */
	class NoSimulationGiven extends Result {
		public NoSimulationGiven() {
			super(true, "No simulation has been given");
		}
	}
}
