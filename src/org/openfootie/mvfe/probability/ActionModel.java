package org.openfootie.mvfe.probability;

import java.util.HashSet;
import java.util.Set;

public abstract class ActionModel {

	protected final double probability;
	private Set<OutcomeModel> outcomes = new HashSet<OutcomeModel>();
	
	public ActionModel(double probability) {
		this.probability = probability;
	}
	
	public void addOutcome(OutcomeModel outcomeModel) {
		outcomes.add(outcomeModel);
	}
	
}
