package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Outline with multiple sections.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlOutline {

	@XmlElement(name = "section")
	private List<XmlSection> sections = new ArrayList<>();

	public List<XmlSection> getSections() {
		return sections;
	}

	public XmlOutline addSection(XmlSection section) {
		sections.add(section);
		return this;
	}
}
