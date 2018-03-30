package de.ralfhergert.flowbox.model;

import de.ralfhergert.math.geom.Mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single simulation.
 */
public class Simulation {

	private Mesh outline;

	private final List<Frame> frames = new ArrayList<>();

	public Mesh getOutline() {
		return outline;
	}

	public Simulation setOutline(Mesh outline) {
		this.outline = outline;
		return this;
	}

	public List<Frame> getFrames() {
		return Collections.unmodifiableList(frames);
	}

	public Simulation appendFrame(Frame frame) {
		frames.add(frame);
		return this;
	}
}
