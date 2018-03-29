package de.ralfhergert.flowbox.model;

/**
 * Super-class for all initializations.
 */
public interface Initializer {

	Result applyTo(Simulation simulation);
}
