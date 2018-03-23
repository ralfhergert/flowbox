package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A single vertex. Can be 2D or 3D.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVertex {

	@XmlAttribute(required = true)
	private double x;
	@XmlAttribute(required = true)
	private double y;
	@XmlAttribute
	private Double z;

	public double getX() {
		return x;
	}

	public XmlVertex setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public XmlVertex setY(double y) {
		this.y = y;
		return this;
	}

	public Double getZ() {
		return z;
	}

	public XmlVertex setZ(Double z) {
		this.z = z;
		return this;
	}

}
