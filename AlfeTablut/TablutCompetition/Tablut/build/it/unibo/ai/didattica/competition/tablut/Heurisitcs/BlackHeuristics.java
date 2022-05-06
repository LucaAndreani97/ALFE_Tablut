package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;


import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

import java.util.HashMap;
import java.util.Map;

public class BlackHeuristics extends Heuristic{

	private static Map<Integer, Double> values = null;
	
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
	
	
	public BlackHeuristics(State state) {
		// TODO Auto-generated constructor stub
		 values = new HashMap<Integer, Double>();
		 values.put(WEIGHT_VICTORY_WHITE, -6000.0);
		 values.put(WEIGHT_VICTORY_BLACK, 10000.0);
	     values.put(WEIGHT_RHOMBUS, 10.0);
	     values.put(WEIGHT_BLOCK_ESCAPE, 15.0); 
	     values.put(WEIGHT_BLACK_ALIVE, 25.0);
	     values.put(WEIGHT_WHITE_EATEN, 30.0);
	     values.put(WEIGHT_BLACK_NEAR_KING, 20.0);
	     //messi in percentuale esclusi victory
	}
	
	public double evaluate(State state) {
		
		double heuristic_value = 0.0;
		Coordinates kingCoords = state.getKingCords();
		
	    //Atomic functions to combine to get utility value through the weighted sum
	    black_alive = (double) state.getNumberOf(State.Pawn.BLACK) / GameAshtonTablut.NUM_BLACK * 100;
	    white_eaten = (double) (GameAshtonTablut.NUM_WHITE - state.getNumberOf(State.Pawn.WHITE)) / GameAshtonTablut.NUM_WHITE * 100;
	    pawns_on_rhombus = (double) getNumberOnRhombus(state) / 8 * 100;
	    block_escape= getNumberOnBlockedEscape(state) / 12 * 100;
	    
	    
	    black_near_king= getBlackNearKing(state,kingCoords)/4*100; //da 0 a ??
	    
	    victory_white = isWhiteWinWithAMove(state,kingCoords); //0,1
	    victory_black = isBlackWinWithAMove(state,kingCoords); //0,1
	    
//	    System.out.println("Black pawns in Rhombus: " + pawns_on_rhombus);
//	    System.out.println("White pawns eaten: " + white_eaten);
//	    System.out.println("Black pawns alive: " + black_alive);
//	    System.out.println("Black near king: " + black_near_king);
//	    System.out.println("Black pawns in Block escape: " + block_escape);
//	    System.out.println("White win in a move: [1:yes,0:no]: " + victory_white);
//	    System.out.println("Black win in a move: [1:yes,0:no]: " + victory_black);
//	       
	    heuristic_value = values.get(0)*victory_white + 
	    			   values.get(1)*victory_black+
	    		       values.get(2)*block_escape+
	    		       values.get(3)*black_alive+
	    		       values.get(4)*white_eaten+
	    		       values.get(5)*black_near_king+
	    		       values.get(5)*pawns_on_rhombus;    		   

	    return heuristic_value;
	}
	
}
