package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;

import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

import java.util.HashMap;
import java.util.Map;

public class BlackHeuristics extends Heuristic{

	final static int WEIGHT_VICTORY_WHITE=0;
	final static int WEIGHT_VICTORY_BLACK=1;
	final static int WEIGHT_BLOCK_ESCAPE = 2;
	final static int WEIGHT_BLACK_ALIVE = 3;
	final static int WEIGHT_WHITE_EATEN = 4;
	final static int WEIGHT_BLACK_NEAR_KING = 5;
	final static int WEIGHT_RHOMBUS=6;
	
	private static double black_alive;
    private static double white_eaten;
    private static double black_near_king;
    private static double block_escape;
    private static double victory_white;
    private static double victory_black;
    private static double pawns_on_rhombus;
	
	private static Map<Integer, Double> weights = null;
	public BlackHeuristics(State state) {
		// TODO Auto-generated constructor stub
		 weights = new HashMap<Integer, Double>();
		 weights.put(WEIGHT_VICTORY_WHITE, -6000.0);
		 weights.put(WEIGHT_VICTORY_BLACK, 10000.0);
	     weights.put(WEIGHT_RHOMBUS, 10.0);
	     weights.put(WEIGHT_BLOCK_ESCAPE, 45.0); //controllare
	     weights.put(WEIGHT_BLACK_ALIVE, 40.0);
	     weights.put(WEIGHT_WHITE_EATEN, 50.0);
	     weights.put(WEIGHT_BLACK_NEAR_KING, 30.0);
	}
	
	public static double evaluateState(State state) {
		
		double utilityValue = 0.0;
		Coordinates kingPos = state.getKingPos();
		
	    //Atomic functions to combine to get utility value through the weighted sum
	    black_alive = (double) state.getNumberOf(State.Pawn.BLACK) / GameAshtonTablut.NUM_BLACK;
	    white_eaten = (double) (GameAshtonTablut.NUM_WHITE - state.getNumberOf(State.Pawn.WHITE)) / GameAshtonTablut.NUM_WHITE;
	    pawns_on_rhombus = (double) getNumberOnRhombus(state) / 8;
	    block_escape= getNumberOfBlockedEscape(state);
	    black_near_king= calculateBlackNearKing(state,kingPos);
	    victory_white = whiteWinWithAMove(state,kingPos);
	    victory_black = blackWinWithAMove(state,kingPos);
	    
	    System.out.println("Number of rhombus: " + pawns_on_rhombus);
	    System.out.println("Number of white pawns eaten: " + white_eaten);
	    System.out.println("Black pawns: " + black_alive);
	    System.out.println("Black near king: " + black_near_king);
	    System.out.println("Block escape: " + block_escape);
	    System.out.println("White win in a move: [1:yes,0:no]: " + victory_white);
	    System.out.println("Black win in a move: [1:yes,0:no]: " + victory_black);
	       
	    utilityValue = weights.get(0)*victory_white + 
	    			   weights.get(1)*victory_black+
	    		       weights.get(2)*block_escape+
	    		       weights.get(3)*black_alive+
	    		       weights.get(4)*white_eaten+
	    		       weights.get(5)*black_near_king+
	    		       weights.get(5)*pawns_on_rhombus;    		   

	    return utilityValue;
	}
	
}
