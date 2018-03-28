package de.ralfhergert.flowbox.model.initialization;

import de.ralfhergert.flowbox.model.Initializer;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * This initializer fill the outline of the simulation with particles.
 */
public class FillOutlineWithParticles implements Initializer {

	private final int numberOfParticles;

	private final double density;

	private final double temperature;

	private Vertex position;
	private Vector velocity;

	public FillOutlineWithParticles(int numberOfParticles, double density, double temperature) {
		this.numberOfParticles = numberOfParticles;
		this.density = density;
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
	public void applyTo(Simulation simulation) {

	}
}
