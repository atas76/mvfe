package org.openfootie.mvfe.probability;

import org.openfootie.mvfe.agent.action.LongPassAction;

public class LongPassActionModel extends ActionModel {
	
	private final LongPassAction action;

	public LongPassActionModel(LongPassAction action, double probability) {
		super(probability);
		this.action = action;
	}
	
	public LongPassAction getAction() {
		return this.action;
	}
}
