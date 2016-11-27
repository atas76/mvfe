package org.openfootie.mvfe.probability;

import java.util.HashSet;
import java.util.Set;

import org.openfootie.mvfe.agent.action.Action;

public class ActionModel {

	private final double probability;
	private final Action action;
	
	private Set<OutcomeModel> outcomes = new HashSet<OutcomeModel>();
	
	public ActionModel(Action action, double probability) {
		this.action = action;
		this.probability = probability;
	}
	
	public void addOutcome(OutcomeModel outcomeModel) {
		outcomes.add(outcomeModel);
	}
}