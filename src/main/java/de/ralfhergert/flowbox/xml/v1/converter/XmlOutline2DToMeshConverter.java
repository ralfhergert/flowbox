package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlEdge;
import de.ralfhergert.flowbox.xml.v1.XmlOutline;
import de.ralfhergert.flowbox.xml.v1.XmlSection;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.generic.converter.Converter;
import de.ralfhergert.math.geom.Face;
import de.ralfhergert.math.geom.Mesh;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * This converter converts a 2D-{@link XmlOutline} into a 3D-{@link Mesh}.
 * The conversion is done by extruding the given outline to z=-0.5 and z=0.5.
 * Resulting in a mesh with a z-thickness of 1.
 */
public class XmlOutline2DToMeshConverter implements Converter<XmlOutline,Mesh> {

	@Override
	public Mesh convert(XmlOutline xmlOutline) {
		if (xmlOutline == null) {
			throw new IllegalArgumentException("outline can not be null");
		}
		Mesh mesh = new Mesh();
		for (XmlSection section : xmlOutline.getSections()) {
			for (XmlEdge edge : section.getEdges()) {
				if (edge.getVertices().size() != 2) {
					throw new IllegalArgumentException("edge is supposed to have two vertices");
				}
				XmlVertex v1 = edge.getVertices().get(0);
				XmlVertex v2 = edge.getVertices().get(1);
				mesh.addFace(new Face()
					.setProperty("name", section.getName())
					.addVertex(new Vertex(new Vector(v1.getX(), v1.getY(), -0.5)))
					.addVertex(new Vertex(new Vector(v2.getX(), v2.getY(), -0.5)))
					.addVertex(new Vertex(new Vector(v2.getX(), v2.getY(),  0.5)))
					.addVertex(new Vertex(new Vector(v1.getX(), v1.getY(),  0.5))));
			}
		}
		return mesh;
	}
}
