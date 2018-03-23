package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A single edge with vertices.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlEdge {

	@XmlElement(name = "vertex")
	private List<XmlVertex> vertices = new ArrayList<>();

	public List<XmlVertex> getVertices() {
		return vertices;
	}

	public XmlEdge addVertex(XmlVertex vertex) {
		vertices.add(vertex);
		return this;
	}
}
