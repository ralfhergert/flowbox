package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.initializer.FillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlFillOutlineWithParticles;
import de.ralfhergert.generic.converter.Converter;

/**
 * This converter converts a {@link XmlFillOutlineWithParticles} into a {@link FillOutlineWithParticles}.
 */
public class XmlFillOutlineWithParticlesConverter implements Converter<XmlFillOutlineWithParticles,FillOutlineWithParticles> {

	@Override
	public FillOutlineWithParticles convert(XmlFillOutlineWithParticles xmlFillOutlineWithParticles) {
		if (xmlFillOutlineWithParticles == null) {
			return null;
		}
		FillOutlineWithParticles fillOutlineWithParticles = new FillOutlineWithParticles(
			xmlFillOutlineWithParticles.getNumberOfParticles(),
			xmlFillOutlineWithParticles.getDensity(),
			xmlFillOutlineWithParticles.getTemperature()
		);
		fillOutlineWithParticles.setVelocity(new XmlVectorConverter().convert(xmlFillOutlineWithParticles.getVelocity()));
		return fillOutlineWithParticles;
	}
}
