package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.flowbox.xml.v1.XmlAddParticle;
import de.ralfhergert.flowbox.xml.v1.XmlFillOutlineWithParticles;
import de.ralfhergert.flowbox.xml.v1.XmlInitialization;
import de.ralfhergert.flowbox.xml.v1.XmlSimulation;
import de.ralfhergert.generic.converter.Converter;

/**
 * This converter converts a {@link XmlSimulation} into a {@link Simulation}.
 */
public class XmlSimulationToSimulationConverter implements Converter<XmlSimulation,Simulation> {

	@Override
	public Simulation convert(XmlSimulation xmlSimulation) {
		if (xmlSimulation == null) {
			throw new IllegalArgumentException("simulation can not be null");
		}
		Simulation simulation = new Simulation();
		if (xmlSimulation.getDimensions() == 2) {
			simulation.setOutline(new XmlOutline2DToMeshConverter().convert(xmlSimulation.getOutline()));
		} else {
			throw new UnsupportedOperationException("conversion is not implemented for " + xmlSimulation.getDimensions() + " dimension");
		}
		if (xmlSimulation.getInitializations() != null) {
			for (XmlInitialization xmlInitialization : xmlSimulation.getInitializations()) {
				if (xmlInitialization instanceof XmlAddParticle) {
					simulation.addInitialization(new XmlAddParticleConverter().convert((XmlAddParticle)xmlInitialization));
				} else if (xmlInitialization instanceof XmlFillOutlineWithParticles) {
					simulation.addInitialization(new XmlFillOutlineWithParticlesConverter().convert((XmlFillOutlineWithParticles)xmlInitialization));
				}
			}
		}
		return simulation;
	}
}
