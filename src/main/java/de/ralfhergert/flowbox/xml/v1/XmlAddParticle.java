package de.ralfhergert.flowbox.xml.v1;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Description element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAddParticle extends XmlInitialization {

	@Positive
	@XmlAttribute(required = true)
	private double mass;

	@PositiveOrZero
	@XmlAttribute(required = true)
	private double temperature;

	@XmlElement(required = true)
	private XmlVertex position;

	@XmlElement(required = true)
	private XmlVector velocity;

	public double getMass() {
		return mass;
	}

	public XmlAddParticle setMass(double mass) {
		this.mass = mass;
		return this;
	}

	public double getTemperature() {
		return temperature;
	}

	public XmlAddParticle setTemperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public XmlVertex getPosition() {
		return position;
	}

	public XmlAddParticle setPosition(XmlVertex position) {
		this.position = position;
		return this;
	}

	public XmlVector getVelocity() {
		return velocity;
	}

	public XmlAddParticle setVelocity(XmlVector velocity) {
		this.velocity = velocity;
		return this;
	}
}
