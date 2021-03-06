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
	private double x = 0;
	@XmlAttribute(required = true)
	private double y = 0;
	@XmlAttribute
	private double z = 0;

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

	public double getZ() {
		return z;
	}

	public XmlVertex setZ(double z) {
		this.z = z;
		return this;
	}

}
