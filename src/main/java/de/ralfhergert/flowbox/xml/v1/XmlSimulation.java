package de.ralfhergert.flowbox.xml.v1;

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

	@XmlElement
	private XmlOutline outline;

	public int getDimensions() {
		return dimensions;
	}

	public List<XmlDescription> getDescriptions() {
		return descriptions;
	}

	public XmlOutline getOutline() {
		return outline;
	}
}
