package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;

/**
 * Super-class for all initializations.
 */
public interface Initializer {

	Result applyTo(Simulation simulation);
}
