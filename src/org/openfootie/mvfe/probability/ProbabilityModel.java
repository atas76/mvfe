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
	
	public static final PitchPosition GK_PITCH_POSITION = new PitchPosition(LengthCoordinate.Gk, null);
	public static final PitchPosition DC_PITCH_POSITION = new PitchPosition(LengthCoordinate.D, WidthCoordinate.C);
	public static final PitchPosition DW_PITCH_POSITION = new PitchPosition(LengthCoordinate.D, WidthCoordinate.W);
	public static final PitchPosition DT_PITCH_POSITION = new PitchPosition(LengthCoordinate.D, WidthCoordinate.T);
	public static final PitchPosition MC_PITCH_POSITION = new PitchPosition(LengthCoordinate.M, WidthCoordinate.C);
	public static final PitchPosition MW_PITCH_POSITION = new PitchPosition(LengthCoordinate.M, WidthCoordinate.W);
	public static final PitchPosition MT_PITCH_POSITION = new PitchPosition(LengthCoordinate.M, WidthCoordinate.T);
	public static final PitchPosition FC_PITCH_POSITION = new PitchPosition(LengthCoordinate.F, WidthCoordinate.C);
	public static final PitchPosition FW_PITCH_POSITION = new PitchPosition(LengthCoordinate.F, WidthCoordinate.W);
	public static final PitchPosition FT_PITCH_POSITION = new PitchPosition(LengthCoordinate.F, WidthCoordinate.T);
	
	public static void init() {
		
		// Action models per starting pitch positions 
		ActionModel [] gkActionModels = new ActionModel[6];
		ActionModel [] dcActionModels = new ActionModel[13];
		
		// Gk action models
		
		gkActionModels[0] = new PassActionModel(new PassAction(DC_PITCH_POSITION), 0.37);
		{
			gkActionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[1] = new PassActionModel(new PassAction(DW_PITCH_POSITION), 0.32);
		{
			gkActionModels[1].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[2] = new LongPassActionModel(new LongPassAction(DW_PITCH_POSITION), 0.1);
		{
			OutcomeModel headerOutcome = new OutcomeModel(Outcome.HEADER, 0.5);
			headerOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MW_PITCH_POSITION, 0.5, false));
			headerOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MW_PITCH_POSITION, 0.5, true));
		
			OutcomeModel failControlOutcome = new OutcomeModel(Outcome.FAIL, 0.25);
			failControlOutcome.setCause(OutcomeCause.CONTROL);
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MW_PITCH_POSITION, 1.0, true));
			
			gkActionModels[2].addOutcome(new OutcomeModel(Outcome.SUCCESS, 0.25));
			gkActionModels[2].addOutcome(headerOutcome);
			gkActionModels[2].addOutcome(failControlOutcome);
		}
		
		gkActionModels[3] = new PassActionModel(new PassAction(MW_PITCH_POSITION), 0.02);
		{
			gkActionModels[3].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		}
		
		gkActionModels[4] = new LongPassActionModel(new LongPassAction(MC_PITCH_POSITION), 0.07);
		{	
			OutcomeModel interceptionHeader = new OutcomeModel(Outcome.INTERCEPTION, 0.67);
			interceptionHeader.setCause(OutcomeCause.HEADER);
			interceptionHeader.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
			
			gkActionModels[4].addOutcome(interceptionHeader);
			gkActionModels[4].addOutcome(new OutcomeModel(Outcome.FAIL, 0.33));
		}
		
		gkActionModels[5] = new LongPassActionModel(new LongPassAction(MW_PITCH_POSITION), 0.12);
		{
			OutcomeModel interceptionHeader = new OutcomeModel(Outcome.INTERCEPTION, 0.2);
			interceptionHeader.setCause(OutcomeCause.HEADER);
			interceptionHeader.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
			
			OutcomeModel headerChallenge = new OutcomeModel(Outcome.HEADER_CHALLENGE, 0.2);
			headerChallenge.getChallengeResults().put(OutcomeChallengeResult.D, 1.0);
			headerChallenge.getChallengeResultsPitchPositionOutcomes().put(OutcomeChallengeResult.D, 
					new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
			
			OutcomeModel failControlOutcome = new OutcomeModel(Outcome.FAIL, 0.4);
			failControlOutcome.setCause(OutcomeCause.CONTROL);
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MT_PITCH_POSITION, 0.5, true));
			failControlOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MW_PITCH_POSITION, 0.5, false));
			
			OutcomeModel throwInOutcome = new OutcomeModel(Outcome.FAIL, 0.2);
			throwInOutcome.addPitchPositionOutcomeModel(
					new PitchPositionOutcomeModel(MT_PITCH_POSITION, 0.2, false));
			
			gkActionModels[5].addOutcome(interceptionHeader);
			gkActionModels[5].addOutcome(headerChallenge);
			gkActionModels[5].addOutcome(failControlOutcome);
			gkActionModels[5].addOutcome(throwInOutcome);
		}
		
		// DC action models
		dcActionModels[0] = new PassActionModel(new PassAction(GK_PITCH_POSITION), 0.13);
		{
			OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 1.0);
			
			dcActionModels[0].addOutcome(success);
		}
		
		// Paths aggregation
		
		aggregatePitchPositionPaths(GK_PITCH_POSITION, gkActionModels);
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
