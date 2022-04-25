package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class Heuristic {
	
	 private final static int[][] rhombus = {
	            {1,2},       {1,6},
	      {2,1},                   {2,7},

	      {6,1},                   {6,7},
	            {7,2},       {7,6}
	 };
	 
	 protected static double calculateBlackNearKing(State state, Coordinates kingCoord) {
			int res=0;
			if(kingCoord.getRow()!=4 && kingCoord.getColumn()!=4)
				for(int i=0; i<9; i++) {
					if(state.getPawn(kingCoord.getRow()+1, i).equals(State.Pawn.BLACK))
						res++;
					if(state.getPawn(kingCoord.getRow()-1, i).equals(State.Pawn.BLACK))
						res++;
					if(state.getPawn(i, kingCoord.getColumn()+1).equals(State.Pawn.BLACK))
						res++;
					if(state.getPawn(i, kingCoord.getColumn()-1).equals(State.Pawn.BLACK))
						res++;
					if(state.getPawn(kingCoord.getRow(), i).equals(State.Pawn.BLACK))
						res++;
					if(state.getPawn(i, kingCoord.getColumn()).equals(State.Pawn.BLACK))
						res++;
				}
			return res;
	}
		
	protected static double calculateWhiteNearKing(State state, Coordinates kingCoord) {
			int res=0;
			for(int i=0; i<9; i++) {
				if(state.getPawn(kingCoord.getRow()+1, i).equals(State.Pawn.WHITE))
					res++;
				if(state.getPawn(kingCoord.getRow()-1, i).equals(State.Pawn.WHITE))
					res++;
				if(state.getPawn(i, kingCoord.getColumn()+1).equals(State.Pawn.WHITE))
					res++;
				if(state.getPawn(i, kingCoord.getColumn()-1).equals(State.Pawn.WHITE))
					res++;
			}
		return res;
	}
	    
	protected static int getNumberOfBlockedEscape(State state){
		int count = 0;
	    int[][] blockedEscapes = {{1,1},{1,2},{1,6},{1,7},{2,1},{2,7},{6,1},{6,7},{7,1},{7,2},{7,6},{7,7}};
	    for (int[] position: blockedEscapes){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())){
	    		count++;
	        }
	    }
	    return count;
	}	
	    
	protected static int whiteWinWithAMove(State state, Coordinates kingCoord) {
		
		if (state.checkRight(kingCoord) == State.Pawn.EMPTY || state.checkLeft(kingCoord) == State.Pawn.EMPTY
				|| state.checkUp(kingCoord) == State.Pawn.EMPTY || state.checkBottom(kingCoord) == State.Pawn.EMPTY) {
			return 1;
		} else
			return 0;
	}
	    
	protected static int blackWinWithAMove(State state, Coordinates kingCoord) {
		
		int count = 0;
			if (state.checkRight(kingCoord) == State.Pawn.EMPTY)
				count++;
			if (state.checkLeft(kingCoord) == State.Pawn.EMPTY)
				count++;
			if (state.checkUp(kingCoord) == State.Pawn.EMPTY)
				count++;
			if (state.checkBottom(kingCoord) == State.Pawn.EMPTY)
				count++;
			if (count > 1)
				return 1;
			else
				return 0;
	}
		
	protected static double getNumberOnRhombus(State state) {
		
		 if (state.getNumberOf(State.Pawn.BLACK) >= 10) {
			 return getValuesOnRhombus(state);
		 }else{
		     return 0;
		 }
	}
	
	private static int getValuesOnRhombus(State state){
		
		int count = 0;
		for (int[] position : rhombus) {
			if (state.getPawn(position[0], position[1]).equalsPawn(State.Pawn.BLACK.toString())) {
				count++;
		    }
		}
		return count;
	}
	
	protected static int numberOfBlackPawn(State state) {
		
		return state.getNumberOf(State.Pawn.BLACK);
	}
	
	protected static int numberOfWhitePawn(State state) {
		
		return state.getNumberOf(State.Pawn.WHITE);
	}
	
}
