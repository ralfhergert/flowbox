package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.math.geom.Vertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link XmlVertexConverter} is working correctly.
 */
public class XmlVertexConverterTest {

	@Test
	public void testConversionOfNullReturnsNull() {
		Assert.assertNull("conversion of null should return null", new XmlVertexConverter().convert(null));
	}

	@Test
	public void testConversionOfXmlVertex() {
		Vertex vertex = new XmlVertexConverter().convert(new XmlVertex().setX(1).setY(2).setZ(3));
		Assert.assertNotNull("vertex should not be null", vertex);
		Assert.assertEquals("x value should be", 1, vertex.getPosition().get(0), 0.000001);
		Assert.assertEquals("x value should be", 2, vertex.getPosition().get(1), 0.000001);
		Assert.assertEquals("x value should be", 3, vertex.getPosition().get(2), 0.000001);
	}
}
