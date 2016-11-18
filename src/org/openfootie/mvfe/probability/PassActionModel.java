package org.openfootie.mvfe.probability;

import org.openfootie.mvfe.agent.action.PassAction;

public class PassActionModel extends ActionModel {

	private final PassAction action;
	private final double probability;
	
	public PassActionModel(PassAction action, double probability) {
		super();
		this.action = action;
		this.probability = probability;
	}

	public PassAction getAction() {
		return action;
	}

	public double getProbability() {
		return probability;
	}
}
