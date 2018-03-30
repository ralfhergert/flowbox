package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.initializer.AddParticle;
import de.ralfhergert.flowbox.xml.v1.XmlAddParticle;
import de.ralfhergert.generic.converter.Converter;

/**
 * This converter converts a {@link XmlAddParticle} into a {@link AddParticle}.
 */
public class XmlAddParticleConverter implements Converter<XmlAddParticle,AddParticle> {

	@Override
	public AddParticle convert(XmlAddParticle xmlAddParticle) {
		if (xmlAddParticle == null) {
			return null;
		}
		AddParticle addParticle = new AddParticle(
			xmlAddParticle.getMass(),
			xmlAddParticle.getTemperature()
		);
		addParticle.setPosition(new XmlVertexConverter().convert(xmlAddParticle.getPosition()));
		addParticle.setVelocity(new XmlVectorConverter().convert(xmlAddParticle.getVelocity()));
		return addParticle;
	}
}
