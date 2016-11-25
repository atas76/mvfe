package org.openfootie.mvfe.probability;

import org.openfootie.mvfe.agent.action.PassAction;

public class PassActionModel extends ActionModel {

	private final PassAction action;
	
	public PassActionModel(PassAction action, double probability) {
		super(probability);
		this.action = action;
	}

	public PassAction getAction() {
		return action;
	}
}
