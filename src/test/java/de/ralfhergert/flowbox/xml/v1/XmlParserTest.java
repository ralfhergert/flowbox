package de.ralfhergert.flowbox.xml.v1;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.InputStream;

/**
 * This test ensure that {@link XmlParser} is working correctly.
 */
public class XmlParserTest {

	@Test
	public void testParsingPressureDistributionDueGravity() throws JAXBException {
		final String fileName = "examples/pressure-distribution-due-gravity.2d.xml";
		InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
		Assert.assertNotNull("resource file '" + fileName + "' could not be found. Was it renamed?", stream);
		XmlSimulation simulation = new XmlParser().parseFrom(stream);
		Assert.assertNotNull("simulation should not be null", simulation);
		Assert.assertEquals("dimensions should be", 2, simulation.getDimensions());
		Assert.assertNotNull("descriptions should not be null", simulation.getDescriptions());
		Assert.assertEquals("number of descriptions", 1, simulation.getDescriptions().size());
		{
			XmlDescription description = simulation.getDescriptions().get(0);
			Assert.assertNotNull("description should not be null", description);
			Assert.assertEquals("language of description", "en", description.getLanguage());
			Assert.assertNotNull("description text is not null", description.getText());
			final String beginning = "This simulation uses a pillar-shaped";
			Assert.assertTrue("text of description should start with '" + beginning + "'", description.getText().startsWith(beginning));
		}
		{
			XmlOutline outline = simulation.getOutline();
			Assert.assertNotNull("outline should not be null", outline);
			Assert.assertNotNull("sections should not be null", outline.getSections());
			Assert.assertEquals("number of sections", 2, outline.getSections().size());
			{
				XmlSection section = outline.getSections().get(0);
				Assert.assertNotNull("first section should not be null", section);
				Assert.assertEquals("first section has no name", null, section.getName());
				Assert.assertNotNull("first section edges should not be null", section.getEdges());
				Assert.assertEquals("number of edges", 3, section.getEdges().size());
				checkEdge(section.getEdges().get(0), 0, 0, 0, 0, 10, 0);
				checkEdge(section.getEdges().get(1), 0, 10, 0, 1, 10, 0);
				checkEdge(section.getEdges().get(2), 1, 10, 0, 1, 0, 0);
			}
			{
				XmlSection section = outline.getSections().get(1);
				Assert.assertNotNull("section should not be null", section);
				Assert.assertEquals("section's name", "bottom", section.getName());
				Assert.assertNotNull("section edges should not be null", section.getEdges());
				Assert.assertEquals("number of edges", 1, section.getEdges().size());
				checkEdge(section.getEdges().get(0), 0, 0, 0, 1, 0, 0);
			}
		}
	}

	private static void checkEdge(XmlEdge edge, double x1, double y1, double z1, double x2, double y2, double z2) {
		Assert.assertNotNull("edge should not be null", edge);
		Assert.assertNotNull("vertices should not be null", edge.getVertices());
		Assert.assertEquals("number of vertices should be", 2, edge.getVertices().size());
		Assert.assertEquals("x of vertex 1", x1, edge.getVertices().get(0).getX(), 0.000001);
		Assert.assertEquals("y of vertex 1", y1, edge.getVertices().get(0).getY(), 0.000001);
		Assert.assertEquals("z of vertex 1", z1, edge.getVertices().get(0).getZ(), 0.000001);
		Assert.assertEquals("x of vertex 2", x2, edge.getVertices().get(1).getX(), 0.000001);
		Assert.assertEquals("y of vertex 2", y2, edge.getVertices().get(1).getY(), 0.000001);
		Assert.assertEquals("z of vertex 2", z2, edge.getVertices().get(1).getZ(), 0.000001);
	}
}
