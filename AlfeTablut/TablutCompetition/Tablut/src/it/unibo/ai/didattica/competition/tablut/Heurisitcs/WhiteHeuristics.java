package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;

import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

import java.util.HashMap;
import java.util.Map;

public class WhiteHeuristics extends Heuristic {
	
	private static Map<Integer, Double> weights = null;
	
	final static int WEIGHT_VICTORY_WHITE=0;
	final static int WEIGHT_VICTORY_BLACK=1;
	final static int WEIGHT_WHITE_ALIVE = 2;
	final static int WEIGHT_BLACK_EATEN = 3;
	final static int WEIGHT_BLACK_NEAR_KING = 4;
	final static int WEIGHT_WHITE_NEAR_KING = 5;

    private static double white_alive;
    private static double black_eaten;
    private static double black_near_king;
    private static double victory_white;
    private static double victory_black;
    private static double white_near_king;
	
	public WhiteHeuristics(State state) {
		weights = new HashMap<Integer, Double>();
		weights.put(WEIGHT_VICTORY_WHITE, 10000.0);
		weights.put(WEIGHT_VICTORY_BLACK, -6000.0);
	    weights.put(WEIGHT_WHITE_ALIVE, 40.0);
	    weights.put(WEIGHT_BLACK_EATEN, 50.0);
	    weights.put(WEIGHT_BLACK_NEAR_KING, -30.0); //intese sia pedine che caselle nere
	    weights.put(WEIGHT_WHITE_NEAR_KING, -30.0);
	}
	
	public static double evaluateState(State state) {
		
		double utilityValue = 0.0;
		Coordinates kingPos = state.getKingPos();

	    //Atomic functions to combine to get utility value through the weighted sum
	    white_alive = (double) state.getNumberOf(State.Pawn.WHITE) / GameAshtonTablut.NUM_WHITE;
	    black_eaten = (double) (GameAshtonTablut.NUM_BLACK - state.getNumberOf(State.Pawn.BLACK)) / GameAshtonTablut.NUM_BLACK;
	    black_near_king= calculateBlackNearKing(state,kingPos);
	    victory_white = whiteWinWithAMove(state,kingPos);
	    victory_black = blackWinWithAMove(state,kingPos);
	    white_near_king = calculateWhiteNearKing(state,kingPos);       
	       
     	System.out.println("Black pawns eaten: " + black_eaten);
	    System.out.println("White pawns alive: " + white_alive);
	    System.out.println("Black near king: " + black_near_king);
	    System.out.println("White near king: " + white_near_king);
	    System.out.println("White win in a move: [1:yes,0:no]: " + victory_white);
	    System.out.println("Black win in a move: [1:yes,0:no]: " + victory_black);
	       
	    utilityValue = weights.get(0)*victory_white + 
	    		   	   weights.get(1)*victory_black+
	    		   	   weights.get(2)*white_alive+
	    		   	   weights.get(3)*black_eaten+
	    		   	   weights.get(4)*black_near_king+
	    		   	   weights.get(5)*white_near_king;

	    return utilityValue;
   }
}
