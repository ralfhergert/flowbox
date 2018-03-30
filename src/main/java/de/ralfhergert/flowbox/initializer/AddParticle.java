package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * This initializer fill the outline of the simulation with particles.
 */
public class AddParticle implements Initializer {

	private final double mass;

	private final double temperature;

	private Vertex position;
	private Vector velocity;

	public AddParticle(double mass, double temperature) {
		if (mass <= 0) {
			throw new IllegalArgumentException("mass must be greater than 0");
		}
		if (temperature < 0) {
			throw new IllegalArgumentException("temperature must be greater than or equal 0");
		}
		this.mass = mass;
		this.temperature = temperature;
	}

	public double getMass() {
		return mass;
	}

	public double getTemperature() {
		return temperature;
	}

	public Vertex getPosition() {
		return position;
	}

	public void setPosition(Vertex position) {
		this.position = position;
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
		if (simulation.getOutline() == null) {
			return new NoSimulationOutlineGiven();
		}
		if (!simulation.getOutline().isImpermeable()) {
			return new SimulationOutlinePermeable();
		}
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
