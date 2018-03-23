package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.flowbox.xml.v1.XmlOutline;
import de.ralfhergert.flowbox.xml.v1.XmlSimulation;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link XmlSimulationToSimulationConverter} is working correctly.
 */
public class XmlSimulationToSimulationConverterTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConversionNullIsRejected() {
		new XmlSimulationToSimulationConverter().convert(null);
	}

	@Test
	public void testConversionOfExample() {
		final XmlSimulation xmlSimulation = new XmlSimulation()
			.setDimension(2)
			.setOutline(new XmlOutline());
		Simulation simulation = new XmlSimulationToSimulationConverter().convert(xmlSimulation);
		Assert.assertNotNull("simulation should not be null", simulation);
		Assert.assertNotNull("outline should not be null", simulation.getOutline());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testConversionOf4DimensionIsNotSupported() {
		final XmlSimulation xmlSimulation = new XmlSimulation().setDimension(4);
		new XmlSimulationToSimulationConverter().convert(xmlSimulation);
	}
}
