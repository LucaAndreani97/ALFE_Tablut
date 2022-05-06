package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;

import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

import java.util.ArrayList;
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
	final static int WEIGHT_KING_MOVE = 6;
	final static int PROTECTION_KING = 7;

    private static double white_alive;
    private static double black_eaten;
    private static double black_near_king;
    private static double victory_white;
    private static double victory_black;
    private static double white_near_king;
    private static double king_move;
    private static double protection_king;
	
	public WhiteHeuristics(State state) {
		values = new HashMap<Integer, Double>();
		values.put(WEIGHT_VICTORY_WHITE, 10000.0);
		values.put(WEIGHT_VICTORY_BLACK, -6000.0);
	    values.put(WEIGHT_WHITE_ALIVE, 15.0);
	    values.put(WEIGHT_BLACK_EATEN, 25.0);
	    values.put(WEIGHT_BLACK_NEAR_KING, -15.0); //intese sia pedine che caselle nere
	    values.put(WEIGHT_WHITE_NEAR_KING, 10.0);
	    values.put(WEIGHT_KING_MOVE, 5.0);
	    values.put(PROTECTION_KING, 30.0);
	}
	
	public double evaluate(State state) {
		
		double heuristic_value = 0.0;
		Coordinates kingCoords = state.getKingCords();

	    //Atomic functions to combine to get utility value through the weighted sum
	    white_alive = (double) state.getNumberOf(State.Pawn.WHITE) / GameAshtonTablut.NUM_WHITE * 100;
	    black_eaten = (double) (GameAshtonTablut.NUM_BLACK - state.getNumberOf(State.Pawn.BLACK)) 
	    		/ GameAshtonTablut.NUM_BLACK * 100;
	    
	    black_near_king= getBlackNearKing(state,kingCoords)/4*100;
	    white_near_king = getWhiteNearKing(state,kingCoords)/4*100;    
	    
	    victory_white = isWhiteWinWithAMove(state,kingCoords); //0,1
	    victory_black = isBlackWinWithAMove(state,kingCoords); //0,1
	    
	    king_move = getKingMove(state, kingCoords);
	    protection_king = protectionKing(state);
	       
	       
//     	System.out.println("Black pawns eaten: " + black_eaten);
//	    System.out.println("White pawns alive: " + white_alive);
//	    System.out.println("Black near king: " + black_near_king);
//	    System.out.println("White near king: " + white_near_king);
//	    System.out.println("White win in a move: [1:yes,0:no]: " + victory_white);
//	    System.out.println("Black win in a move: [1:yes,0:no]: " + victory_black);
//	    System.out.println("King move: " + king_move);
	       
	    heuristic_value = values.get(0)*victory_white + 
	    		   	   values.get(1)*victory_black+
	    		   	   values.get(2)*white_alive+
	    		   	   values.get(3)*black_eaten+
	    		   	   values.get(4)*black_near_king+
	    		   	   values.get(5)*white_near_king+
	    		   	   values.get(6)*king_move+
	    		   	   values.get(7)*protection_king;

	    return heuristic_value;
   }
	

   private double protectionKing(State state){

       //Values whether there is only a white pawn near to the king
       final double VAL_NEAR = 0.6;
       final double VAL_TOT = 1.0;

       double result = 0.0;

       Coordinates kingPos = state.getKingCords();
       //Pawns near to the king
       ArrayList<int[]> pawnsPositions = (ArrayList<int[]>) positionNearPawns(state,kingPos,State.Pawn.BLACK.toString());

       //There is a black pawn that threatens the king and 2 pawns are enough to eat the king
       if (pawnsPositions.size() == 1 && getNumEatenPositions(state) == 2){
           int[] enemyPos = pawnsPositions.get(0);
           //Used to store other position from where king could be eaten
           int[] targetPosition = new int[2];
           //Enemy right to the king
           if(enemyPos[0] == kingPos.getRow() && enemyPos[1] == kingPos.getColumn() + 1){
               //Left to the king there is a white pawn and king is protected
               targetPosition[0] = kingPos.getRow();
               targetPosition[1] = kingPos.getColumn() - 1;
               if (state.getPawn(targetPosition[0],targetPosition[1]).equalsPawn(State.Pawn.WHITE.toString())){
                   result += VAL_NEAR;
               }
           //Enemy left to the king
           }else if(enemyPos[0] == kingPos.getRow() && enemyPos[1] == kingPos.getColumn() -1){
               //Right to the king there is a white pawn and king is protected
               targetPosition[0] = kingPos.getRow();
               targetPosition[1] = kingPos.getColumn() + 1;
               if(state.getPawn(targetPosition[0],targetPosition[1]).equalsPawn(State.Pawn.WHITE.toString())){
                   result += VAL_NEAR;
               }
           //Enemy up to the king
           }else if(enemyPos[1] == kingPos.getColumn() && enemyPos[0] == kingPos.getRow() - 1){
               //Down to the king there is a white pawn and king is protected
               targetPosition[0] = kingPos.getRow() + 1;
               targetPosition[1] = kingPos.getColumn();
               if(state.getPawn(targetPosition[0], targetPosition[1]).equalsPawn(State.Pawn.WHITE.toString())){
                   result += VAL_NEAR;
               }
           //Enemy down to the king
           }else{
               //Up there is a white pawn and king is protected
               targetPosition[0] = kingPos.getRow() - 1;
               targetPosition[1] = kingPos.getColumn();
               if(state.getPawn(targetPosition[0], targetPosition[1]).equalsPawn(State.Pawn.WHITE.toString())){
                   result += VAL_NEAR;
               }
           }

           //Considering whites to use as barriers for the target pawn
           double otherPoints = VAL_TOT - VAL_NEAR;
           double contributionPerN = 0.0;

           //Whether it is better to keep free the position
           if (targetPosition[0] == 0 || targetPosition[0] == 8 || targetPosition[1] == 0 || targetPosition[1] == 8){
               if(state.getPawn(targetPosition[0],targetPosition[1]).equalsPawn(State.Pawn.EMPTY.toString())){
                   result = 1.0;
               } else {
                   result = 0.0;
               }
           }else{
               //Considering a reduced number of neighbours whether target is near to citadels or throne
               if (targetPosition[0] == 4 && targetPosition[1] == 2 || targetPosition[0] == 4 && targetPosition[1] == 6
                       || targetPosition[0] == 2 && targetPosition[1] == 4 || targetPosition[0] == 6 && targetPosition[1] == 4
                       || targetPosition[0] == 3 && targetPosition[1] == 4 || targetPosition[0] == 5 && targetPosition[1] == 4
                       || targetPosition[0] == 4 && targetPosition[1] == 3 || targetPosition[0] == 4 && targetPosition[1] == 5){
                   contributionPerN = otherPoints / 2;
               }else{
                   contributionPerN = otherPoints / 3;
               }

               result += contributionPerN * checkNearPawns(state, targetPosition,State.Pawn.WHITE.toString());
           }

       }
       return result;
   }
	
}
