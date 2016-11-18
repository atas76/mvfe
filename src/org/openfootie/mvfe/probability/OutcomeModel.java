package org.openfootie.mvfe.probability;

public class OutcomeModel {
	
	private final Outcome outcome;
	private final double probability;
	
	public OutcomeModel(Outcome outcome, double probability) {
		super();
		this.outcome = outcome;
		this.probability = probability;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public double getProbability() {
		return probability;
	}
}
