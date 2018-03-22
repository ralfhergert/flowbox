package de.ralfhergert.flowbox.xml.v1;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Parser for simulation-xml-files.
 */
public class XmlParser {

	private final Unmarshaller unmarshaller;

	public XmlParser() {
		try {
			unmarshaller = JAXBContext.newInstance(XmlSimulation.class).createUnmarshaller();
		} catch (JAXBException e) {
			throw new IllegalArgumentException("could not instantiate unmarshaller", e);
		}
	}

	public XmlSimulation parseFrom(InputStream stream) throws JAXBException {
		return (XmlSimulation)unmarshaller.unmarshal(stream);
	}
}
