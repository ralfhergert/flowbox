package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.flowbox.initializer.FillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlFillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlSimulation;
import de.ralfhergert.generic.converter.Converter;

/**
 * This converter converts a {@link XmlSimulation} into a {@link Simulation}.
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
		fillOutlineWithParticles.setPosition(new XmlVertexConverter().convert(xmlFillOutlineWithParticles.getPosition()));
		fillOutlineWithParticles.setVelocity(new XmlVectorConverter().convert(xmlFillOutlineWithParticles.getVelocity()));
		return fillOutlineWithParticles;
	}
}
