package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlEdge;
import de.ralfhergert.flowbox.xml.v1.XmlOutline;
import de.ralfhergert.flowbox.xml.v1.XmlSection;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.math.geom.Face;
import de.ralfhergert.math.geom.Mesh;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link XmlOutline2DToMeshConverter} is working correctly.
 */
public class XmlOutline2DToMeshConverterTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConverterRejectsToConvertNull() {
		new XmlOutline2DToMeshConverter().convert(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConversionOfIncompleteEdge() {
		// create an outline with an edge with only one vertex
		final XmlOutline outline = new XmlOutline().addSection(
			new XmlSection().addEdge(
				new XmlEdge().addVertex(new XmlVertex())));
		new XmlOutline2DToMeshConverter().convert(outline);
	}

	@Test
	public void testConvertingEmptyOutlineResultsInEmptyMesh() {
		Mesh mesh = new XmlOutline2DToMeshConverter().convert(new XmlOutline());
		Assert.assertNotNull("mesh should not be null", mesh);
		Assert.assertEquals("mesh should not have any faces", 0, mesh.getFaces().size());
	}

	@Test
	public void testConvertingSectionWithNoEdgesResultsInEmptyMesh() {
		Mesh mesh = new XmlOutline2DToMeshConverter().convert(new XmlOutline().addSection(new XmlSection()));
		Assert.assertNotNull("mesh should not be null", mesh);
		Assert.assertEquals("mesh should not have any faces", 0, mesh.getFaces().size());
	}

	@Test
	public void testConvertingSingleSectionResultsInMeshWithOneFace() {
		Mesh mesh = new XmlOutline2DToMeshConverter().convert(new XmlOutline()
			.addSection(new XmlSection().addEdge(new XmlEdge()
				.addVertex(new XmlVertex().setX(1).setY(2).setZ(null))
				.addVertex(new XmlVertex().setX(3).setY(4).setZ(null)))));
		Assert.assertNotNull("mesh should not be null", mesh);
		Assert.assertEquals("mesh should have one face", 1, mesh.getFaces().size());
		Face face = mesh.getFaces().get(0);
		Assert.assertNotNull("face should not be null", face);
		Assert.assertEquals("face should not have a property name", null, face.getProperty("name"));
		Assert.assertEquals("face should have 4 vertices", 4, face.getVertices().size());
		Assert.assertEquals("x of vertex 1",    1, face.getVertices().get(0).getPosition().get(0), 0.000001);
		Assert.assertEquals("y of vertex 1",    2, face.getVertices().get(0).getPosition().get(1), 0.000001);
		Assert.assertEquals("y of vertex 1", -0.5, face.getVertices().get(0).getPosition().get(2), 0.000001);
		Assert.assertEquals("x of vertex 2",    3, face.getVertices().get(1).getPosition().get(0), 0.000001);
		Assert.assertEquals("y of vertex 2",    4, face.getVertices().get(1).getPosition().get(1), 0.000001);
		Assert.assertEquals("y of vertex 2", -0.5, face.getVertices().get(1).getPosition().get(2), 0.000001);
		Assert.assertEquals("x of vertex 3",    3, face.getVertices().get(2).getPosition().get(0), 0.000001);
		Assert.assertEquals("y of vertex 3",    4, face.getVertices().get(2).getPosition().get(1), 0.000001);
		Assert.assertEquals("y of vertex 3",  0.5, face.getVertices().get(2).getPosition().get(2), 0.000001);
		Assert.assertEquals("x of vertex 4",    1, face.getVertices().get(3).getPosition().get(0), 0.000001);
		Assert.assertEquals("y of vertex 4",    2, face.getVertices().get(3).getPosition().get(1), 0.000001);
		Assert.assertEquals("y of vertex 4",  0.5, face.getVertices().get(3).getPosition().get(2), 0.000001);
	}

	@Test
	public void testOnConversionSectionNameIsTransferedToFace() {
		// create an outline with a section with a name.
		Mesh mesh = new XmlOutline2DToMeshConverter().convert(new XmlOutline()
			.addSection(new XmlSection()
				.setName("foobar")
				.addEdge(new XmlEdge()
					.addVertex(new XmlVertex().setX(1).setY(2))
					.addVertex(new XmlVertex().setX(3).setY(4)))));
		Assert.assertNotNull("mesh should not be null", mesh);
		Assert.assertEquals("mesh should have one face", 1, mesh.getFaces().size());
		Face face = mesh.getFaces().get(0);
		Assert.assertNotNull("face should not be null", face);
		Assert.assertEquals("face should have a property name being", "foobar", face.getProperty("name"));
	}
}
