package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class Heuristic {
	
	 private final static int[][] rhombus = {{1,2},{1,6},{2,1},{2,7},{6,1},{6,7},{7,2},{7,6}};
	 
	 protected static double getBlackNearKing(State state, Coordinates kingCoord) {
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
		
	protected static double getWhiteNearKing(State state, Coordinates kingCoord) {
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
	    
	protected static int getNumberOnBlockedEscape(State state){
		int count = 0;
	    int[][] blockedEscapes = {{1,1},{1,2},{1,6},{1,7},{2,1},{2,7},{6,1},{6,7},{7,1},{7,2},{7,6},{7,7}};
	    for (int[] position: blockedEscapes){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())){
	    		count++;
	        }
	    }
	    return count;
	}	
	    
	protected static int isWhiteWinWithAMove(State state, Coordinates kingCoord) {
		
		if (state.checkRight(kingCoord) == State.Pawn.EMPTY || state.checkLeft(kingCoord) == State.Pawn.EMPTY
				|| state.checkUp(kingCoord) == State.Pawn.EMPTY || state.checkBottom(kingCoord) == State.Pawn.EMPTY) {
			return 1;
		} else
			return 0;
	}
	
	    
protected static int isBlackWinWithAMove(State state, Coordinates kingCoord) {
		
		int count=0;
		
		if ( kingCoord.getRow()==4 && kingCoord.getColumn() == 4) {
			//re nel trono, servono 4 pedine nere attorno
				if(state.getPawn(kingCoord.getRow()+1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
					count++;
				if(state.getPawn(kingCoord.getRow()-1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
					count++;
				if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()+1).equals(State.Pawn.BLACK))
					count++;
				if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()-1).equals(State.Pawn.BLACK))
					count++;
				if (count == 4) {
					return 1;
				}
		}else if (kingNearThrone(kingCoord)) {
			//se Ã¨ affianco al trono, ne servono 3
			if(state.getPawn(kingCoord.getRow()+1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow()-1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()+1).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()-1).equals(State.Pawn.BLACK))
				count++;
			if (count == 3) {
				return 1;
			}
			return 1;
			
		}else {
			//in tutti gli altri casi 2
			if(state.getPawn(kingCoord.getRow()+1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow()-1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()+1).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()-1).equals(State.Pawn.BLACK))
				count++;
			if (count == 2) {
				return 1;
			}
			return 1;
		}
			return 0;
	}
		
	private static boolean kingNearThrone(Coordinates kingCoord) {
		int row=kingCoord.getRow();
		int col=kingCoord.getColumn();
		if ( (row == 4 && col == 5) || (row == 4 && col == 3) || 
				(row ==3 && col ==4) || (row == 5 && col == 4) )
			return true;
		return false;
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
