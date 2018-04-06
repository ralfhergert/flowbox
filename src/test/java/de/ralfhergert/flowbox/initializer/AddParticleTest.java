package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;
import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link AddParticle} is working correctly.
 */
public class AddParticleTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticleRejectsZeroMass() {
		new AddParticle(0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticleRejectsZeroGasConstant() {
		new AddParticle(1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticleRejectsNegativeTemperature() {
		new AddParticle(1, 1, -1);
	}

	@Test
	public void testApplyingToNoSimulation() {
		Result result = new AddParticle(1, 1, 0).applyTo(null);
		Assert.assertTrue("result should be an error", result.isError());
		Assert.assertEquals("result should be", AddParticle.NoSimulationGiven.class, result.getClass());
	}

	@Test
	public void testApplyingToEmptySimulation() {
		// create a simulation with a cubical outline.
		final Simulation simulation = new Simulation();
		Result result = new AddParticle(1, 1.1, 0)
			.setPosition(new Vertex(new Vector(0.1, 0.2, 0.3)))
			.setVelocity(new Vector(0.4, 0.5, 0.6))
			.applyTo(simulation);
		Assert.assertTrue("result should be a success", result.isSuccess());
		Assert.assertNotNull("simulation frames should not be null", simulation.getFrames());
		Assert.assertEquals("simulation should have one frame", 1, simulation.getFrames().size());
		Frame frame = simulation.getFrames().get(0);
		Assert.assertNotNull("frame should not be null", frame);
		Assert.assertEquals("frame's timestamp should be", 0, frame.getTimestampNs(), 0.000001);
		Assert.assertNotNull("frame's particles should not be null", frame.getParticles());
		Assert.assertEquals("number of particles in frame", 1, frame.getParticles().size());
		Particle particle = frame.getParticles().get(0);
		Assert.assertNotNull("particle null not be null", particle);
		Assert.assertEquals("mass of particle should be", 1, particle.getMass(), 0.000001);
		Assert.assertEquals("specificGasConstant of particle should be", 1.1, particle.getSpecificGasConstant(), 0.000001);
		Assert.assertEquals("temperature of particle should be", 0, particle.getTemperature(), 0.000001);
		Assert.assertEquals("position of particle should be", new Vertex(new Vector(0.1, 0.2, 0.3)), particle.getPosition());
		Assert.assertEquals("velocity of particle should be", new Vector(0.4, 0.5, 0.6), particle.getVelocity());
	}

	@Test
	public void testApplyingToSimulationWithPreparedFrame() {
		// create a simulation with a cubical outline.
		final Simulation simulation = new Simulation().appendFrame(new Frame(0));
		Result result = new AddParticle(1, 287, 0)
			.setPosition(new Vertex(new Vector(0.1, 0.2, 0.3)))
			.setVelocity(new Vector(0.4, 0.5, 0.6))
			.applyTo(simulation);
		Assert.assertTrue("result should be a success", result.isSuccess());
		Assert.assertNotNull("simulation frames should not be null", simulation.getFrames());
		Assert.assertEquals("simulation should have one frame", 1, simulation.getFrames().size());
		Frame frame = simulation.getFrames().get(0);
		Assert.assertNotNull("frame should not be null", frame);
		Assert.assertEquals("frame's timestamp should be", 0, frame.getTimestampNs(), 0.000001);
		Assert.assertNotNull("frame's particles should not be null", frame.getParticles());
		Assert.assertEquals("number of particles in frame", 1, frame.getParticles().size());
		Particle particle = frame.getParticles().get(0);
		Assert.assertNotNull("particle null not be null", particle);
		Assert.assertEquals("mass of particle should be", 1, particle.getMass(), 0.000001);
		Assert.assertEquals("specifiGasConstant of particle should be", 287, particle.getSpecificGasConstant(), 0.000001);
		Assert.assertEquals("temperature of particle should be", 0, particle.getTemperature(), 0.000001);
		Assert.assertEquals("position of particle should be", new Vertex(new Vector(0.1, 0.2, 0.3)), particle.getPosition());
		Assert.assertEquals("velocity of particle should be", new Vector(0.4, 0.5, 0.6), particle.getVelocity());
	}
}
