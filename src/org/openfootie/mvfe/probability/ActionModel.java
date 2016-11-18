package org.openfootie.mvfe.probability;

import java.util.HashSet;
import java.util.Set;

public abstract class ActionModel {

	private Set<OutcomeModel> outcomes = new HashSet<OutcomeModel>();
	
	public void addOutcome(OutcomeModel outcomeModel) {
		outcomes.add(outcomeModel);
	}
	
}
