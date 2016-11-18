package org.openfootie.mvfe.probability;

import java.util.HashSet;
import java.util.Set;

import org.openfootie.mvfe.space.PitchPosition;

public class ProbabilityModelPath {
	
	private final PitchPosition pitchPosition;
	private Set<ActionModel> actionModels = new HashSet<ActionModel>();
	
	public ProbabilityModelPath(PitchPosition pitchPosition) {
		super();
		this.pitchPosition = pitchPosition;
	}

	public Set<ActionModel> getActionModels() {
		return actionModels;
	}

	public void setActionModels(Set<ActionModel> actionModels) {
		this.actionModels = actionModels;
	}
	
	public void addActionModel(ActionModel actionModel) {
		this.actionModels.add(actionModel);
	}

	public PitchPosition getPitchPosition() {
		return pitchPosition;
	}
}
