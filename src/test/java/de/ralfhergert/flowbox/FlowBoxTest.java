package de.ralfhergert.flowbox;

import de.ralfhergert.flowbox.model.Result;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

/**
 * This test ensures that {@link FlowBox} is working correctly.
 */
public class FlowBoxTest {

	@Test
	public void testNoSimulationDefinitionResultIsGivenOnNull() {
		Result result = new FlowBox().runFromStream(null);
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", FlowBox.NoSimulationDefinitionGiven.class, result.getClass());
	}

	@Test
	public void testSimulationDefinitionNotParsableResultIsGiven() throws UnsupportedEncodingException {
		// use a XML-stream with no root element.
		Result result = new FlowBox().runFromStream(new ByteArrayInputStream("<?xml?>".getBytes("UTF-8")));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", FlowBox.SimulationDefinitionNotParsable.class, result.getClass());
	}

	@Test
	public void testSimulationDefinitionNotValidResultIsGiven() throws UnsupportedEncodingException {
		// use an empty simulation definition (with no outline).
		Result result = new FlowBox().runFromStream(new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<simulation dimensions=\"2\"/>".getBytes("UTF-8")));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", FlowBox.SimulationDefinitionNotValid.class, result.getClass());
	}

	@Test
	public void testSimulationDefinitionNotConvertibleResultIsGiven() throws UnsupportedEncodingException {
		// use a simulation begin defined in 4D.
		Result result = new FlowBox().runFromStream(new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<simulation dimensions=\"4\">" +
			"  <outline/>" +
			"</simulation>").getBytes("UTF-8")));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", FlowBox.SimulationDefinitionNotConvertible.class, result.getClass());
	}

	@Test
	public void testSimulationOutlineIsLeakingResultIsGiven() throws UnsupportedEncodingException {
		// use a simulation with an empty outline.
		Result result = new FlowBox().runFromStream(new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<simulation dimensions=\"2\">" +
			"  <outline/>" +
			"</simulation>").getBytes("UTF-8")));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", FlowBox.SimulationOutlineIsLeaking.class, result.getClass());
	}

	@Test
	public void testSimulationWithNoFramesEnds() throws UnsupportedEncodingException {
		// use a simulation with a valid outline but no initialization.
		Result result = new FlowBox().runFromStream(new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<simulation dimensions=\"2\">" +
			"  <outline>" +
			"    <section>\n" +
			"      <edge><vertex x=\"-0.5\" y=\"-0.5\"/><vertex x=\"+0.5\" y=\"-0.5\"/></edge>\n" +
			"      <edge><vertex x=\"+0.5\" y=\"-0.5\"/><vertex x=\"+0.5\" y=\"+0.5\"/></edge>\n" +
			"      <edge><vertex x=\"+0.5\" y=\"+0.5\"/><vertex x=\"-0.5\" y=\"+0.5\"/></edge>\n" +
			"      <edge><vertex x=\"-0.5\" y=\"+0.5\"/><vertex x=\"-0.5\" y=\"-0.5\"/></edge>\n" +
			"    </section>\n" +
			"  </outline>" +
			"</simulation>").getBytes("UTF-8")));
		Assert.assertNotNull("result should not be null", result);
		Assert.assertEquals("result should be", new Result(false, "Done"), result);
	}
}
