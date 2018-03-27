package de.ralfhergert.flowbox.xml.v1;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Root element for simulation-xmls.
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

	@XmlElementWrapper(name = "initialization")
	@XmlElements({
		@XmlElement(name = "fillOutlineWithParticles", type = XmlFillOutlineWithParticles.class)
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
