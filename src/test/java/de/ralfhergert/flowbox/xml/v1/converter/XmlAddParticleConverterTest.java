package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.initializer.AddParticle;
import de.ralfhergert.flowbox.xml.v1.XmlAddParticle;
import de.ralfhergert.flowbox.xml.v1.XmlVector;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link XmlAddParticleConverter} is working correctly.
 */
public class XmlAddParticleConverterTest {

	@Test
	public void testConversionOfNullReturnsNull() {
		Assert.assertNull("conversion of null should return null", new XmlAddParticleConverter().convert(null));
	}

	@Test
	public void testConversionOfFillOutlineWithParticles() {
		AddParticle addParticle = new XmlAddParticleConverter()
			.convert(new XmlAddParticle()
				.setMass(2)
				.setSpecificGasConstant(287)
				.setTemperature(300)
				.setPosition(new XmlVertex())
				.setVelocity(new XmlVector()));
		Assert.assertNotNull("fillOutlineWithParticles should not be null", addParticle);
		Assert.assertEquals("mass should be", 2, addParticle.getMass(), 0.000001);
		Assert.assertEquals("specificGasConstant should be", 287, addParticle.getSpecificGasConstant(), 0.000001);
		Assert.assertEquals("temperature should be", 300, addParticle.getTemperature(), 0.000001);
		Assert.assertNotNull("position should not be null", addParticle.getPosition());
		Assert.assertNotNull("velocity should not be null", addParticle.getVelocity());
	}
}
