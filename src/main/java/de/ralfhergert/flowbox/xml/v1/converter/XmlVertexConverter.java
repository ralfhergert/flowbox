package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.generic.converter.Converter;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * This converter converts a {@link XmlVertex} into a {@link Vertex}.
 */
public class XmlVertexConverter implements Converter<XmlVertex,Vertex> {

	@Override
	public Vertex convert(XmlVertex xmlVertex) {
		if (xmlVertex == null) {
			return null;
		}
		return new Vertex(new Vector(xmlVertex.getX(), xmlVertex.getY(), xmlVertex.getZ()));
	}
}
