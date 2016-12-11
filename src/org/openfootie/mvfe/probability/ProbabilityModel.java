package org.openfootie.mvfe.probability;

import java.util.HashMap;
import java.util.Map;

import org.openfootie.mvfe.agent.action.DribbleAction;
import org.openfootie.mvfe.agent.action.FoulAction;
import org.openfootie.mvfe.agent.action.KickBallAction;
import org.openfootie.mvfe.agent.action.LongPassAction;
import org.openfootie.mvfe.agent.action.PassAction;
import org.openfootie.mvfe.agent.action.StealAction;
import org.openfootie.mvfe.space.LengthCoordinate;
import org.openfootie.mvfe.space.PitchPosition;
import org.openfootie.mvfe.space.WidthCoordinate;

public class ProbabilityModel {

	private static Map<PitchPosition, ProbabilityModelPath> normalFlow = new HashMap<PitchPosition, ProbabilityModelPath>();
	private static Map<PitchPosition, ProbabilityModelPath> freeKick = new HashMap<PitchPosition, ProbabilityModelPath>();
	
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
	public static final PitchPosition PC_PITCH_POSITION = new PitchPosition(LengthCoordinate.P, WidthCoordinate.C);
	public static final PitchPosition PW_PITCH_POSITION = new PitchPosition(LengthCoordinate.P, WidthCoordinate.W);
	
	public static void init() {
		
		// Gk action models
		{
			ActionModel [] actionModels = new ActionModel[6];
			
			actionModels[0] = new ActionModel(new PassAction(DC_PITCH_POSITION), 0.37);
			{
				actionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
		
			actionModels[1] = new ActionModel(new PassAction(DW_PITCH_POSITION), 0.32);
			{
				actionModels[1].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
		
			actionModels[2] = new ActionModel(new LongPassAction(DW_PITCH_POSITION), 0.1);
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
			
				actionModels[2].addOutcome(new OutcomeModel(Outcome.SUCCESS, 0.25));
				actionModels[2].addOutcome(headerOutcome);
				actionModels[2].addOutcome(failControlOutcome);
			}
		
			actionModels[3] = new ActionModel(new PassAction(MW_PITCH_POSITION), 0.02);
			{
				actionModels[3].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
		
			actionModels[4] = new ActionModel(new LongPassAction(MC_PITCH_POSITION), 0.07);
			{	
				OutcomeModel interceptionHeader = new OutcomeModel(Outcome.INTERCEPTION, 0.67);
				interceptionHeader.setCause(OutcomeCause.HEADER);
				interceptionHeader.addPitchPositionOutcomeModel(
						new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
			
				actionModels[4].addOutcome(interceptionHeader);
				actionModels[4].addOutcome(new OutcomeModel(Outcome.FAIL, 0.33));
			}
		
			actionModels[5] = new ActionModel(new LongPassAction(MW_PITCH_POSITION), 0.12);
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
			
				actionModels[5].addOutcome(interceptionHeader);
				actionModels[5].addOutcome(headerChallenge);
				actionModels[5].addOutcome(failControlOutcome);
				actionModels[5].addOutcome(throwInOutcome);
			}
			
			aggregatePitchPositionPaths(GK_PITCH_POSITION, actionModels);
		}
		
		// DC action models
		{
			ActionModel [] actionModels = new ActionModel[13];
			
			actionModels[0] = new ActionModel(new PassAction(GK_PITCH_POSITION), 0.13);
			{
				OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 1.0);
			
				actionModels[0].addOutcome(success);
			}
		
			actionModels[1] = new ActionModel(new PassAction(DC_PITCH_POSITION), 0.17);
			{
				OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 0.89);
			
				OutcomeModel failControlOutcome = new OutcomeModel(Outcome.FAIL, 0.11);
				failControlOutcome.setCause(OutcomeCause.CONTROL);
				failControlOutcome.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(FC_PITCH_POSITION, 1.0, true));
			
				actionModels[1].addOutcome(success);
				actionModels[1].addOutcome(failControlOutcome);
			}
		
			actionModels[2] = new ActionModel(new PassAction(DW_PITCH_POSITION), 0.23);
			{
				OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 1.0);
			
				actionModels[2].addOutcome(success);
			}
		
			actionModels[3] = new ActionModel(new PassAction(DW_PITCH_POSITION), 0.02);
			{
				OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 1.0);
			
				actionModels[3].addOutcome(success);
			}
		
			actionModels[4] = new ActionModel(new LongPassAction(FW_PITCH_POSITION), 0.02);
			{
				OutcomeModel fail = new OutcomeModel(Outcome.FAIL, 1.0);
				fail.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(GK_PITCH_POSITION, 1.0, true));
			
				actionModels[4].addOutcome(fail);
			}
			
			actionModels[5] = new ActionModel(new PassAction(MC_PITCH_POSITION), 0.19);
			{
				actionModels[5].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[6] = new ActionModel(new PassAction(MW_PITCH_POSITION), 0.08);
			{
				actionModels[6].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[7] = new ActionModel(new LongPassAction(FC_PITCH_POSITION), 0.04);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 0.5);
				interception.setCause(OutcomeCause.KICK);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(FT_PITCH_POSITION, 1.0, false));
				
				OutcomeModel fail = new OutcomeModel(Outcome.FAIL, 0.5);
				fail.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(GK_PITCH_POSITION, 1.0, true));
				
				actionModels[7].addOutcome(interception);
				actionModels[7].addOutcome(fail);
			}
			
			actionModels[8] = new ActionModel(new LongPassAction(FW_PITCH_POSITION), 0.02);
			{
				OutcomeModel fail = new OutcomeModel(Outcome.FAIL, 1.0);
				fail.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(FT_PITCH_POSITION, 1.0, true));
				
				actionModels[8].addOutcome(fail);
			}
			
			actionModels[9] = new ActionModel(new KickBallAction(DC_PITCH_POSITION), 0.02);
			{
				OutcomeModel possessionChange = new OutcomeModel(Outcome.FAIL, 1.0);
				possessionChange.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(FC_PITCH_POSITION, 1.0, true));
				
				actionModels[9].addOutcome(possessionChange);
			}
			
			actionModels[10] = new ActionModel(new KickBallAction(MC_PITCH_POSITION), 0.02);
			{
				OutcomeModel headerChallenge = new OutcomeModel(Outcome.HEADER_CHALLENGE, 1.0);
				headerChallenge.getChallengeResults().put(OutcomeChallengeResult.L, 1.0);
				headerChallenge.getChallengeResultsPitchPositionOutcomes().put(OutcomeChallengeResult.L, 
						new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
				
				actionModels[10].addOutcome(headerChallenge);
			}
			
			actionModels[11] = new ActionModel(new DribbleAction(), 0.02);
			{
				actionModels[11].addOutcome(new OutcomeModel(Outcome.OFFENSIVE_FOUL, 1.0));
			}
			
			actionModels[12] = new ActionModel(new StealAction(), 0.04);
			{
				actionModels[12].addOutcome(new OutcomeModel(Outcome.FOUL, 0.5));
				actionModels[12].addOutcome(new OutcomeModel(Outcome.STEAL, 0.5));
			}
			
			aggregatePitchPositionPaths(DC_PITCH_POSITION, actionModels);
		}
		
		// DW action models
		{
			// Initialize parameters
			final int AM_CARDINALITY = 11;
			PitchPosition pitchPosition = DW_PITCH_POSITION;
			
			ActionModel [] actionModels = new ActionModel[AM_CARDINALITY];
			
			// Define action models
			
			actionModels[0] = new ActionModel(new PassAction(GK_PITCH_POSITION), 0.23);
			{
				actionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[1] = new ActionModel(new PassAction(DC_PITCH_POSITION), 0.14);
			{
				actionModels[1].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[2] = new ActionModel(new PassAction(DW_PITCH_POSITION), 0.35);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 0.12);
				interception.setCause(OutcomeCause.BLOCK);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(DT_PITCH_POSITION, 1.0, false));
				
				actionModels[2].addOutcome(new OutcomeModel(Outcome.SUCCESS, 0.88));
				actionModels[2].addOutcome(interception);
			}
			
			actionModels[3] = new ActionModel(new PassAction(MC_PITCH_POSITION), 0.06);
			{
				OutcomeModel fail = new OutcomeModel(Outcome.FAIL, 0.33);
				fail.setCause(OutcomeCause.CONTROL);
				fail.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(PC_PITCH_POSITION, 1.0, true));
				
				actionModels[3].addOutcome(new OutcomeModel(Outcome.SUCCESS, 0.67));
				actionModels[3].addOutcome(fail);
			}
			
			actionModels[4] = new ActionModel(new PassAction(MW_PITCH_POSITION), 0.08);
			{
				OutcomeModel success = new OutcomeModel(Outcome.SUCCESS, 0.75);
				
				OutcomeModel fail = new OutcomeModel(Outcome.FAIL, 0.25);
				fail.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(MT_PITCH_POSITION, 1.0, true));
				
				actionModels[4].addOutcome(success);
				actionModels[4].addOutcome(fail);
			}
			
			actionModels[5] = new ActionModel(new LongPassAction(GK_PITCH_POSITION), 0.02);
			{
				actionModels[5].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[6] = new ActionModel(new LongPassAction(MC_PITCH_POSITION), 0.02);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 1.0);
				interception.setCause(OutcomeCause.HEADER);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
				
				actionModels[6].addOutcome(interception);
			}
			
			actionModels[7] = new ActionModel(new LongPassAction(MW_PITCH_POSITION), 0.02);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 1.0);
				interception.setCause(OutcomeCause.HEADER);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, false));
				
				actionModels[7].addOutcome(interception);
			}
			
			actionModels[8] = new ActionModel(new LongPassAction(FC_PITCH_POSITION), 0.02);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 1.0);
				interception.setCause(OutcomeCause.HEADER);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(MC_PITCH_POSITION, 1.0, true));
				
				actionModels[8].addOutcome(interception);
			}
			
			actionModels[9] = new ActionModel(new DribbleAction(), 0.02);
			{
				actionModels[9].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[10] = new ActionModel(new FoulAction(), 0.04);
					
			// Add pitch position paths to probability model tree
			aggregatePitchPositionPaths(pitchPosition, actionModels);
		}
		
		// DT action models
		{
			final int AM_CARDINALITY = 2;
			PitchPosition pitchPosition = DT_PITCH_POSITION;
					
			ActionModel [] actionModels = new ActionModel[AM_CARDINALITY];
					
			// Define action models (the actual work)
			actionModels[0] = new ActionModel(new PassAction(DW_PITCH_POSITION), 0.5);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 1.0);
				interception.setCause(OutcomeCause.HEADER);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(DW_PITCH_POSITION, 1.0, false));
				
				actionModels[0].addOutcome(interception);
			}
			
			actionModels[1] = new ActionModel(new PassAction(MC_PITCH_POSITION), 0.5);
			{
				OutcomeModel interception = new OutcomeModel(Outcome.INTERCEPTION, 1.0);
				interception.setCause(OutcomeCause.HEADER);
				interception.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(MW_PITCH_POSITION, 1.0, true));
				
				actionModels[1].addOutcome(interception);
			}
					
			// Add pitch position paths to probability model tree
			aggregatePitchPositionPaths(pitchPosition, actionModels);
		}
		
		// Free kicks @ DW
		{
			// Initialize parameters
			final int AM_CARDINALITY = 4;
			PitchPosition pitchPosition = DW_PITCH_POSITION;
					
			ActionModel [] actionModels = new ActionModel[AM_CARDINALITY];
					
			// Define action models (the actual work)
			
			actionModels[0] = new ActionModel(new PassAction(GK_PITCH_POSITION), 0.25);
			{
				actionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[1] = new ActionModel(new PassAction(DC_PITCH_POSITION), 0.25);
			{
				actionModels[1].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[2] = new ActionModel(new PassAction(MW_PITCH_POSITION), 0.25);
			{
				actionModels[2].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
			}
			
			actionModels[3] = new ActionModel(new LongPassAction(MC_PITCH_POSITION), 0.25);
			{
				OutcomeModel header = new OutcomeModel(Outcome.HEADER, 1.0);
				header.addPitchPositionOutcomeModel(new PitchPositionOutcomeModel(DC_PITCH_POSITION, 1.0, false));
				
				actionModels[3].addOutcome(header);
			}
					
			// Add pitch position paths to probability model tree
			aggregatePitchPositionPaths(freeKick, pitchPosition, actionModels);
		}
		
		// Template for adding pitch position path
		{
			// TODO: Initialize parameters
			final int AM_CARDINALITY = -1;
			PitchPosition pitchPosition = null;
			
			ActionModel [] actionModels = new ActionModel[AM_CARDINALITY];
			
			// TODO: Define action models (the actual work)
			
			// Add pitch position paths to probability model tree
			aggregatePitchPositionPaths(pitchPosition, actionModels);
		}
	}
	
	private static void aggregatePitchPositionPaths(PitchPosition pos, ActionModel[] actionModels) {
		aggregatePitchPositionPaths(null, pos, actionModels);
	}
	
	private static void aggregatePitchPositionPaths(Map<PitchPosition, ProbabilityModelPath> root, PitchPosition pos, ActionModel[] actionModels) {
		
		// Position should be not null for this utility method. Check added to avoid potential errors while debugging; no need for reporting 
		if (pos == null) {
			return;
		}
		
		for (int i = 0; i < actionModels.length; i++) {
			
			ProbabilityModelPath path = new ProbabilityModelPath(pos);
			path.addActionModel(actionModels[i]);
			
			if (root == null) {
				normalFlow.put(path.getPitchPosition(), path);
			} else {
				root.put(path.getPitchPosition(), path);
			}
		}
	}
}
