package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Description element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVector {

	@XmlAttribute(required = true)
	private double x;

	@XmlAttribute(required = true)
	private double y;

	@XmlAttribute(required = true)
	private double z;

	public double getX() {
		return x;
	}

	public XmlVector setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public XmlVector setY(double y) {
		this.y = y;
		return this;
	}

	public double getZ() {
		return z;
	}

	public XmlVector setZ(double z) {
		this.z = z;
		return this;
	}
}
