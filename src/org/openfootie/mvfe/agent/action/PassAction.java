package org.openfootie.mvfe.agent.action;

import org.openfootie.mvfe.space.PitchPosition;

public class PassAction extends Action {
	
	private PitchPosition target;

	public PassAction(PitchPosition target) {
		super();
		this.target = target;
	}

	public PitchPosition getTarget() {
		return target;
	}

	public void setTarget(PitchPosition target) {
		this.target = target;
	}
}
