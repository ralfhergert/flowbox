package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.model.initializer.FillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlFillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlVector;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link XmlFillOutlineWithParticlesConverter} is working correctly.
 */
public class XmlFillOutlineWithParticlesConverterTest {

	@Test
	public void testConversionOfNullReturnsNull() {
		Assert.assertNull("conversion of null should return null", new XmlFillOutlineWithParticlesConverter().convert(null));
	}

	@Test
	public void testConversionOfFillOutlineWithParticles() {
		FillOutlineWithParticles fillOutlineWithParticles = new XmlFillOutlineWithParticlesConverter()
			.convert(new XmlFillOutlineWithParticles()
				.setNumberOfParticles(42)
				.setDensity(2)
				.setTemperature(300)
				.setPosition(new XmlVertex())
				.setVelocity(new XmlVector()));
		Assert.assertNotNull("fillOutlineWithParticles should not be null", fillOutlineWithParticles);
		Assert.assertEquals("numberOfParticles should be", 42, fillOutlineWithParticles.getNumberOfParticles());
		Assert.assertEquals("density should be", 2, fillOutlineWithParticles.getDensity(), 0.000001);
		Assert.assertEquals("temperature should be", 300, fillOutlineWithParticles.getTemperature(), 0.000001);
		Assert.assertNotNull("position should not be null", fillOutlineWithParticles.getPosition());
		Assert.assertNotNull("velocity should not be null", fillOutlineWithParticles.getVelocity());
	}
}
