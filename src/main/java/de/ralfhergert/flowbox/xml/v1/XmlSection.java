package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Section with multiple edges.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSection {

	@XmlAttribute
	private String name;

	@XmlElement(name = "edge")
	private List<XmlEdge> edges;

	public String getName() {
		return name;
	}

	public List<XmlEdge> getEdges() {
		return edges;
	}
}
