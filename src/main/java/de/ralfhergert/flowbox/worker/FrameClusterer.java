package de.ralfhergert.flowbox.worker;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;

/**
 * This worker clusters the particles of a frame.
 */
public class FrameClusterer {

	public Frame clusterFrame(Frame frame) {
		if (frame == null) {
			throw new IllegalArgumentException("frame can not be null");
		}
		// initialize each particle with the simulation outline.
		for (Particle particle : frame.getParticles()) {
			particle.setOutline(AffiliatedMeshCreator.createAffiliatedMesh(frame.getSimulation().getOutline()));
		}
		frame.setClustered(true);
		return frame;
	}
}
