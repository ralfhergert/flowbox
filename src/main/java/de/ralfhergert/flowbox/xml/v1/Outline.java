package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Outline with multiple sections.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Outline {

	@XmlElement(name = "section")
	private List<Section> sections;

	public List<Section> getSections() {
		return sections;
	}
}
