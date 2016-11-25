package org.openfootie.mvfe.probability;

import java.util.HashMap;
import java.util.Map;

import org.openfootie.mvfe.agent.action.LongPassAction;
import org.openfootie.mvfe.agent.action.PassAction;
import org.openfootie.mvfe.space.LengthCoordinate;
import org.openfootie.mvfe.space.PitchPosition;
import org.openfootie.mvfe.space.WidthCoordinate;

public class ProbabilityModel {

	private static Map<PitchPosition, ProbabilityModelPath> root = new HashMap<PitchPosition, ProbabilityModelPath>();
	
	
	public static void init() {
		
		// Starting pitch positions
		PitchPosition gkPos = new PitchPosition(LengthCoordinate.Gk, null);
		
		// Action models per starting pitch positions 
		ActionModel [] gkActionModels = new ActionModel[6];
		
		// Gk action models
		
		gkActionModels[0] = new PassActionModel(new PassAction(new PitchPosition(LengthCoordinate.D, WidthCoordinate.C)), 0.37);
		{
			gkActionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[1] = new PassActionModel(new PassAction(new PitchPosition(LengthCoordinate.D, WidthCoordinate.W)), 0.32);
		{
			gkActionModels[1].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[2] = new LongPassActionModel(new LongPassAction(new PitchPosition(LengthCoordinate.D, WidthCoordinate.W)), 0.1);
		{
			OutcomeModel headerOutcome = new OutcomeModel(Outcome.HEADER, 0.5);
			headerOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W), 0.5, false));
			headerOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W), 0.5, true));
		
			OutcomeModel failControlOutcome = new OutcomeModel(Outcome.FAIL, 0.25);
			failControlOutcome.setCause(OutcomeCause.CONTROL);
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W), 1.0, true));
			
			gkActionModels[2].addOutcome(new OutcomeModel(Outcome.SUCCESS, 0.25));
			gkActionModels[2].addOutcome(headerOutcome);
			gkActionModels[2].addOutcome(failControlOutcome);
		}
		
		gkActionModels[3] = new PassActionModel(new PassAction(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W)), 0.02);
		{
			gkActionModels[3].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[4] = new LongPassActionModel(new LongPassAction(new PitchPosition(LengthCoordinate.M, WidthCoordinate.C)), 0.07);
		{	
			OutcomeModel interceptionHeader = new OutcomeModel(Outcome.INTERCEPTION, 0.67);
			interceptionHeader.setCause(OutcomeCause.HEADER);
			interceptionHeader.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.C), 1.0, true));
			
			gkActionModels[4].addOutcome(interceptionHeader);
			gkActionModels[4].addOutcome(new OutcomeModel(Outcome.FAIL, 0.33));
		}
		
		gkActionModels[5] = new LongPassActionModel(new LongPassAction(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W)), 0.12);
		{
			OutcomeModel interceptionHeader = new OutcomeModel(Outcome.INTERCEPTION, 0.2);
			interceptionHeader.setCause(OutcomeCause.HEADER);
			interceptionHeader.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.C), 1.0, true));
			
			OutcomeModel headerChallenge = new OutcomeModel(Outcome.HEADER_CHALLENGE, 0.2);
			headerChallenge.getChallengeResults().put(OutcomeChallengeResult.D, 1.0);
			headerChallenge.getChallengeResultsPitchPositionOutcomes().put(OutcomeChallengeResult.D, 
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.C), 1.0, true));
			
			OutcomeModel failControlOutcome = new OutcomeModel(Outcome.FAIL, 0.4);
			failControlOutcome.setCause(OutcomeCause.CONTROL);
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.T), 0.5, true));
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.W), 0.5, false));
			
			OutcomeModel throwInOutcome = new OutcomeModel(Outcome.FAIL, 0.2);
			throwInOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(new PitchPosition(LengthCoordinate.M, WidthCoordinate.T), 0.2, false));
			
			gkActionModels[5].addOutcome(interceptionHeader);
			gkActionModels[5].addOutcome(headerChallenge);
			gkActionModels[5].addOutcome(failControlOutcome);
			gkActionModels[5].addOutcome(throwInOutcome);
		}
		
		// Paths aggregation
		
		aggregatePitchPositionPaths(gkPos, gkActionModels);
	}

	private static void aggregatePitchPositionPaths(PitchPosition gkPos, ActionModel[] gkActionModels) {
		for (int i = 0; i < gkActionModels.length; i++) {
			
			ProbabilityModelPath path = new ProbabilityModelPath(gkPos);
			path.addActionModel(gkActionModels[i]);
			
			addPath(path);
		}
	}
	private static void addPath(ProbabilityModelPath path) {
		root.put(path.getPitchPosition(), path);
	}
}
