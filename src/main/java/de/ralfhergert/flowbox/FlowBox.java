package de.ralfhergert.flowbox;

import de.ralfhergert.flowbox.model.ExceptionalResult;
import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.flowbox.xml.v1.XmlParser;
import de.ralfhergert.flowbox.xml.v1.XmlSimulation;
import de.ralfhergert.flowbox.xml.v1.converter.XmlSimulationToSimulationConverter;
import de.ralfhergert.math.geom.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.Set;

/**
 * This is the entry point into the simulation.
 */
public class FlowBox {

	private static final Logger LOG = LoggerFactory.getLogger(FlowBox.class);

	/**
	 * Runs a simulation.
	 * @param stream is expected to deliver a simulation description.
	 */
	public Result runFromStream(InputStream stream) {
		if (stream == null) {
			return new NoSimulationDefinitionGiven("stream can not be null");
		}
		LOG.trace("trying to parse simulation description");
		final XmlSimulation xmlSimulation;
		try {
			xmlSimulation = new XmlParser().parseFrom(stream);
		} catch (JAXBException e) {
			return new SimulationDefinitionNotParsable("could not parse given simulation description", e);
		}
		LOG.trace("trying to validate simulation description");
		Set<ConstraintViolation<XmlSimulation>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(xmlSimulation);
		if (!violations.isEmpty()) {
			return new SimulationDefinitionNotValid("simulation description is invalid", violations);
		}
		LOG.trace("trying to convert simulation description into internal data model");
		final Simulation simulation;
		try {
			simulation = new XmlSimulationToSimulationConverter().convert(xmlSimulation);
		} catch (Exception e) {
			return new SimulationDefinitionNotConvertible("could not convert simulation description into data model", e);
		}
		LOG.trace("verifying outline mesh");
		if (!simulation.getOutline().isImpermeable()) {
			return new SimulationOutlineIsLeaking("simulation outline is not a closed mesh", simulation.getOutline().getOpenEdges());
		}
		LOG.trace("trying to initialize");
		return new Result(false, "Done");
	}

	/**
	 * This result means that no usable simulation definition could be found.
	 */
	public class NoSimulationDefinitionGiven extends Result {

		public NoSimulationDefinitionGiven(String message) {
			super(true, message);
		}
	}

	/**
	 * This result means that the simulation definition could not be parsed.
	 */
	public class SimulationDefinitionNotParsable extends ExceptionalResult {

		public SimulationDefinitionNotParsable(String message, Exception exception) {
			super(message, exception);
		}
	}

	/**
	 * This result means that the simulation definition is not valid.
	 */
	public class SimulationDefinitionNotValid extends Result {

		private Set<ConstraintViolation<XmlSimulation>> violations;

		public SimulationDefinitionNotValid(String message, Set<ConstraintViolation<XmlSimulation>> violations) {
			super(true, message);
			this.violations = violations;
		}
	}

	/**
	 * This result means that the simulation definition could not be converted into the internal data model.
	 */
	public class SimulationDefinitionNotConvertible extends ExceptionalResult {

		public SimulationDefinitionNotConvertible(String message, Exception exception) {
			super(message, exception);
		}
	}

	/**
	 * This result means that the outline mesh is not closed and leaking.
	 */
	public class SimulationOutlineIsLeaking extends Result {

		private Set<Edge> openEdges;

		public SimulationOutlineIsLeaking(String message, Set<Edge> openEdges) {
			super(true, message);
			this.openEdges = openEdges;
		}
	}
}
