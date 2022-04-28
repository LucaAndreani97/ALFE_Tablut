package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;



public class MyIterativeMinMax extends IterativeDeepeningAlphaBetaSearch<State, Action, State.Turn>{
	
	public MyIterativeMinMax(Game<State, Action, Turn> tablutGame, double minValue, double maxValue, int searchTime) {
		super(tablutGame, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, searchTime);
	}

	@Override
	public Action makeDecision(State state) {
		return super.makeDecision(state);
	}
	
	@Override
	protected double eval(State state, State.Turn player) {
		super.eval(state, player);
		return game.getUtility(state, player);
	}

}
