package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Root element for simulation-xmls.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "simulation")
public class Simulation {

	@XmlAttribute(required = true)
	private int dimensions;

	@XmlElement(name = "description")
	private List<Description> descriptions;

	@XmlElement
	private Outline outline;

	public int getDimensions() {
		return dimensions;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public Outline getOutline() {
		return outline;
	}
}
