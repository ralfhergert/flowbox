package de.ralfhergert.flowbox.model;

import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * Represents a single particle.
 */
public class Particle {

	private final double mass;

	private final double temperature;

	private Vertex position = new Vertex(new Vector(3));
	private Vector velocity = new Vector(3);

	public Particle(double mass, double temperature) {
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

	public Particle setPosition(Vertex position) {
		this.position = position;
		return this;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public Particle setVelocity(Vector velocity) {
		this.velocity = velocity;
		return this;
	}
}
