package de.ralfhergert.flowbox.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link Simulation} is working correctly.
 */
public class SimulationTest {

	@Test
	public void testEmptySimulationHasNoLastFrame() {
		Assert.assertNull("no last frame should exist", new Simulation().getLastFrame());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendingNullFrameIsRejected() {
		new Simulation().appendFrame(null);
	}

	@Test
	public void testSimulationRegistersAtFrameWhenAppending() {
		final Frame frame = new Frame(0);
		Assert.assertNull("no simulation should be registered", frame.getSimulation());
		final Simulation simulation = new Simulation().appendFrame(frame);
		Assert.assertEquals("simulation should be registered at frame", simulation, frame.getSimulation());
	}

	@Test
	public void testAppendingFrame() {
		final Simulation simulation = new Simulation();
		Assert.assertNotNull("simulation's frame list should not be null", simulation.getFrames());
		Assert.assertEquals("simulation should have no frames", 0, simulation.getFrames().size());
		simulation.appendFrame(new Frame(0));
		Assert.assertEquals("simulation should have one frames", 1, simulation.getFrames().size());
	}
}
