package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * A single edge with vertices.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {

	@XmlElement(name = "vertex")
	private List<Vertex> vertices;

	public List<Vertex> getVertices() {
		return vertices;
	}
}
