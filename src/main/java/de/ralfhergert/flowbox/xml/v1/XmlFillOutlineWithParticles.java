package de.ralfhergert.flowbox.xml.v1;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Description element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFillOutlineWithParticles extends XmlInitialization {

	@Min(1)
	@XmlAttribute(required = true)
	private int numberOfParticles;

	@Positive
	@XmlAttribute(required = true)
	private double density;

	@Positive
	@XmlAttribute(required = true)
	private double specificGasConstant;

	@PositiveOrZero
	@XmlAttribute(required = true)
	private double temperature;

	@XmlElement(name = "velocity")
	private XmlVector velocity;

	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	public XmlFillOutlineWithParticles setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
		return this;
	}

	public double getDensity() {
		return density;
	}

	public XmlFillOutlineWithParticles setDensity(double density) {
		this.density = density;
		return this;
	}

	public double getSpecificGasConstant() {
		return specificGasConstant;
	}

	public XmlFillOutlineWithParticles setSpecificGasConstant(double specificGasConstant) {
		this.specificGasConstant = specificGasConstant;
		return this;
	}

	public double getTemperature() {
		return temperature;
	}

	public XmlFillOutlineWithParticles setTemperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public XmlVector getVelocity() {
		return velocity;
	}

	public XmlFillOutlineWithParticles setVelocity(XmlVector velocity) {
		this.velocity = velocity;
		return this;
	}
}
