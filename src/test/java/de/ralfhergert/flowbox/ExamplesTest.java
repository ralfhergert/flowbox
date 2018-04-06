package de.ralfhergert.flowbox;

import de.ralfhergert.flowbox.model.Result;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that all examples are valid.
 */
public class ExamplesTest {

	@Test
	public void verifySingleParticleFloatingWithGravity2D() {
		Result result = new FlowBox().runFromStream(getClass().getClassLoader().getResourceAsStream("examples/single-particle-floating-with-gravity.2d.xml"));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", new Result(false, "Done"), result);
	}

	@Test
	public void verifyPressureDistributionDueGravity2D() {
		Result result = new FlowBox().runFromStream(getClass().getClassLoader().getResourceAsStream("examples/pressure-distribution-due-gravity.2d.xml"));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", new Result(false, "Done"), result);
	}
}
