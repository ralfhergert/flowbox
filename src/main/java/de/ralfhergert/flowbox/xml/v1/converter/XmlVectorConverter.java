package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlVector;
import de.ralfhergert.generic.converter.Converter;
import org.alltiny.math.vector.Vector;

/**
 * This converter converts a {@link XmlVector} into a {@link Vector}.
 */
public class XmlVectorConverter implements Converter<XmlVector,Vector> {

	@Override
	public Vector convert(XmlVector xmlVector) {
		if (xmlVector == null) {
			return null;
		}
		return new Vector(xmlVector.getX(), xmlVector.getY(), xmlVector.getZ());
	}
}
