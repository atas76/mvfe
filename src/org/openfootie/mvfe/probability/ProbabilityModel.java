package org.openfootie.mvfe.probability;

import java.util.HashMap;
import java.util.Map;

import org.openfootie.mvfe.agent.action.PassAction;
import org.openfootie.mvfe.space.LengthCoordinate;
import org.openfootie.mvfe.space.PitchPosition;
import org.openfootie.mvfe.space.WidthCoordinate;

public class ProbabilityModel {

	private static Map<PitchPosition, ProbabilityModelPath> root = new HashMap<PitchPosition, ProbabilityModelPath>();
	
	private static void addPath(ProbabilityModelPath path) {
		root.put(path.getPitchPosition(), path);
	}
	
	public static void init() {
		
		PitchPosition gkPos = new PitchPosition(LengthCoordinate.Gk, null);
		ActionModel [] gkActionModels = new ActionModel[6];
		
		gkActionModels[0] = new PassActionModel(new PassAction(new PitchPosition(LengthCoordinate.D, WidthCoordinate.C)), 0.37);
		gkActionModels[0].addOutcome(new OutcomeModel(Outcome.SUCCESS, 1.0));
		
		for (int i = 0; i < gkActionModels.length; i++) {
			
			ProbabilityModelPath path = new ProbabilityModelPath(gkPos);
			path.addActionModel(gkActionModels[i]);
			
			addPath(path);
		}
	}
}
