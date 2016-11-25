package org.openfootie.mvfe.probability;

import java.util.HashMap;
import java.util.Map;

import org.openfootie.mvfe.space.PitchPosition;

public class OutcomeModel {
	
	private final Outcome outcome;
	private final double probability;
	
	private OutcomeCause cause;
	private Map<OutcomeChallengeResult, Double> challengeResults = new HashMap<>();

	private Map<PitchPosition, PitchPositionOutcomeModel> pitchPositionOutcomes = new HashMap<>();
	private Map<OutcomeChallengeResult, PitchPositionOutcomeModel> challengeResultsPitchPositionOutcomes = new HashMap<>();
	
	public OutcomeModel(Outcome outcome, double probability) {
		super();
		this.outcome = outcome;
		this.probability = probability;
	}
	
	public void addPitchPositionOutcomeModel(PitchPositionOutcomeModel model) {
		pitchPositionOutcomes.put(model.getPitchPosition(), model);
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public double getProbability() {
		return probability;
	}

	public OutcomeCause getCause() {
		return cause;
	}

	public void setCause(OutcomeCause cause) {
		this.cause = cause;
	}

	public Map<PitchPosition, PitchPositionOutcomeModel> getPitchPositionOutcomes() {
		return pitchPositionOutcomes;
	}

	public void setPitchPositionOutcomes(Map<PitchPosition, PitchPositionOutcomeModel> pitchPositionOutcomes) {
		this.pitchPositionOutcomes = pitchPositionOutcomes;
	}

	public Map<OutcomeChallengeResult, Double> getChallengeResults() {
		return challengeResults;
	}

	public void setChallengeResults(Map<OutcomeChallengeResult, Double> challengeResults) {
		this.challengeResults = challengeResults;
	}

	public Map<OutcomeChallengeResult, PitchPositionOutcomeModel> getChallengeResultsPitchPositionOutcomes() {
		return challengeResultsPitchPositionOutcomes;
	}

	public void setChallengeResultsPitchPositionOutcomes(
			Map<OutcomeChallengeResult, PitchPositionOutcomeModel> challengeResultsPitchPositionOutcomes) {
		this.challengeResultsPitchPositionOutcomes = challengeResultsPitchPositionOutcomes;
	}
}
