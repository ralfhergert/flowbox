package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Description element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDescription {

	@XmlAttribute(name = "lang", required = true)
	private String language;

	@XmlValue
	private String text;

	public String getLanguage() {
		return language;
	}

	public String getText() {
		return text;
	}
}
