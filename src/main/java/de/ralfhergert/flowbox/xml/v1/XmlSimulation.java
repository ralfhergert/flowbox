package de.ralfhergert.flowbox.xml.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Root element for simulation-XMLs.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "simulation")
public class XmlSimulation {

	@XmlAttribute(required = true)
	private int dimensions;

	@XmlElement(name = "description")
	private List<XmlDescription> descriptions;

	@NotNull
	@XmlElement
	private XmlOutline outline;

	@Valid
	@XmlElementWrapper(name = "initialization")
	@XmlElements({
		@XmlElement(name = "fillOutlineWithParticles", type = XmlFillOutlineWithParticles.class),
		@XmlElement(name = "addParticle", type = XmlAddParticle.class)
	})
	private List<XmlInitialization> initializations;

	public int getDimensions() {
		return dimensions;
	}

	public XmlSimulation setDimension(int dimension) {
		this.dimensions = dimension;
		return this;
	}

	public List<XmlDescription> getDescriptions() {
		return descriptions;
	}

	public XmlOutline getOutline() {
		return outline;
	}

	public XmlSimulation setOutline(XmlOutline outline) {
		this.outline = outline;
		return this;
	}
}
