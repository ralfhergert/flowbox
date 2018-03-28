package de.ralfhergert.flowbox.model;

/**
 * Super-class for all initializations.
 */
public interface Initializer {

	void applyTo(Simulation simulation);
}
