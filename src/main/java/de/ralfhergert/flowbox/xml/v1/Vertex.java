package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A single vertex. Can be 2D or 3D.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Vertex {

	@XmlAttribute(required = true)
	private double x;
	@XmlAttribute(required = true)
	private double y;
	@XmlAttribute
	private Double z;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Double getZ() {
		return z;
	}
}
