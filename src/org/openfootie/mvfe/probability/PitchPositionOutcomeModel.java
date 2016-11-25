package org.openfootie.mvfe.probability;

import org.openfootie.mvfe.space.PitchPosition;

public class PitchPositionOutcomeModel {
	
	private PitchPosition pitchPosition;
	private double probability;
	private boolean possessionChange;
	
	public PitchPositionOutcomeModel(PitchPosition pitchPosition, double probability, boolean possessionChange) {
		super();
		this.pitchPosition = pitchPosition;
		this.probability = probability;
		this.possessionChange = possessionChange;
	}
	
	public PitchPosition getPitchPosition() {
		return pitchPosition;
	}
	public void setPitchPosition(PitchPosition pitchPosition) {
		this.pitchPosition = pitchPosition;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public boolean isPossessionChange() {
		return possessionChange;
	}
	public void setPossessionChange(boolean possessionChange) {
		this.possessionChange = possessionChange;
	}
}
