package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;

import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

import java.util.HashMap;
import java.util.Map;

public class WhiteHeuristics extends Heuristic {
	
	private static Map<Integer, Double> values = null;
	
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
		values = new HashMap<Integer, Double>();
		values.put(WEIGHT_VICTORY_WHITE, 10000.0);
		values.put(WEIGHT_VICTORY_BLACK, -6000.0);
	    values.put(WEIGHT_WHITE_ALIVE, 20.0);
	    values.put(WEIGHT_BLACK_EATEN, 35.0);
	    values.put(WEIGHT_BLACK_NEAR_KING, -15.0); //intese sia pedine che caselle nere
	    values.put(WEIGHT_WHITE_NEAR_KING, 30.0);
	}
	
	public double evaluate(State state) {
		
		double heuristic_value = 0.0;
		Coordinates kingCoords = state.getKingCords();

	    //Atomic functions to combine to get utility value through the weighted sum
	    white_alive = (double) state.getNumberOf(State.Pawn.WHITE) / GameAshtonTablut.NUM_WHITE * 100;
	    black_eaten = (double) (GameAshtonTablut.NUM_BLACK - state.getNumberOf(State.Pawn.BLACK)) 
	    		/ GameAshtonTablut.NUM_BLACK * 100;
	    
	    //hanno senso così???
	    black_near_king= getBlackNearKing(state,kingCoords) / GameAshtonTablut.NUM_BLACK * 100;
	    white_near_king = getWhiteNearKing(state,kingCoords) / GameAshtonTablut.NUM_WHITE * 100;    
	    
	    victory_white = isWhiteWinWithAMove(state,kingCoords); //0,1
	    victory_black = isBlackWinWithAMove(state,kingCoords); //0,1
	       
	       
     	System.out.println("Black pawns eaten: " + black_eaten);
	    System.out.println("White pawns alive: " + white_alive);
	    System.out.println("Black near king: " + black_near_king);
	    System.out.println("White near king: " + white_near_king);
	    System.out.println("White win in a move: [1:yes,0:no]: " + victory_white);
	    System.out.println("Black win in a move: [1:yes,0:no]: " + victory_black);
	       
	    heuristic_value = values.get(0)*victory_white + 
	    		   	   values.get(1)*victory_black+
	    		   	   values.get(2)*white_alive+
	    		   	   values.get(3)*black_eaten+
	    		   	   values.get(4)*black_near_king+
	    		   	   values.get(5)*white_near_king;

	    return heuristic_value;
   }
}
