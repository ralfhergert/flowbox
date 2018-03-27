package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlVector;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link XmlVectorConverter} is working correctly.
 */
public class XmlVectorConverterTest {

	@Test
	public void testConversionOfNullReturnsNull() {
		Assert.assertNull("conversion of null should return null", new XmlVectorConverter().convert(null));
	}

	@Test
	public void testConversionOfXmlVertex() {
		Vector vector = new XmlVectorConverter().convert(new XmlVector().setX(1).setY(2).setZ(3));
		Assert.assertNotNull("vertex should not be null", vector);
		Assert.assertEquals("x value should be", 1, vector.get(0), 0.000001);
		Assert.assertEquals("x value should be", 2, vector.get(1), 0.000001);
		Assert.assertEquals("x value should be", 3, vector.get(2), 0.000001);
	}
}
