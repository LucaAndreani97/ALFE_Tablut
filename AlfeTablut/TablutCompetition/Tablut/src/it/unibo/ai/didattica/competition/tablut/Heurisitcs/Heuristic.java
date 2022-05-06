package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class Heuristic {
	
	private final static int[][] rhombus = {{1,2},{1,6},{2,1},{2,7},{6,1},{6,7},{7,2},{7,6}};
 
	static double[][] kingMove = new double[9][9];
	static boolean[][] citadels=new boolean [9][9];
	static {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				kingMove[i][j] += Math.abs(j - 4) + Math.abs(i - 4);
				if (kingMove[i][j] > 4)
					kingMove[i][j] = 4;
			}
		}
		kingMove[2][6] = 1;
		kingMove[2][2] = 1;
		kingMove[6][6] = 1;
		kingMove[6][2] = 1;
		kingMove[1][3] = 0.2;
		kingMove[1][5] = 0.2;
		kingMove[7][3] = 0.2;
		kingMove[7][5] = 0.2;
		kingMove[3][1] = 0.2;
		kingMove[3][7] = 0.2;
		kingMove[5][1] = 0.2;
		kingMove[5][7] = 0.2;
		kingMove[4][5] = 0.1;
		kingMove[5][4] = 0.1;
		kingMove[3][4] = 0.1;
		kingMove[4][3] = 0.1;
		kingMove[4][2] = 0.1;
		kingMove[6][4] = 0.1;
		kingMove[2][4] = 0.1;
		kingMove[4][6] = 0.1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				citadels[i][j]=false;
			}
		}
		citadels[0][3]=true;
		citadels[0][4]=true;
		citadels[0][5]=true;
		citadels[1][4]=true;

		citadels[3][0]=true;
		citadels[4][0]=true;
		citadels[5][0]=true;
		citadels[4][1]=true;

		citadels[8][3]=true;
		citadels[8][4]=true;
		citadels[8][5]=true;
		citadels[7][4]=true;

		citadels[3][8]=true;
		citadels[4][8]=true;
		citadels[5][8]=true;
		citadels[4][7]=true;

		citadels[4][4]=true;
	}
	
	protected static double getKingMove(State state, Coordinates kingCoor) {
		
		int quad = checkBlockEscape(state);
		
		double resKingMove = 0;
		
		if (quad != 0 ) {
			if (quad == 1 && kingCoor.getRow() < 5 && kingCoor.getColumn() < 5) {
				resKingMove = kingMove[kingCoor.getRow()][kingCoor.getColumn()];
			} else if (quad == 2 && kingCoor.getRow() < 5 && kingCoor.getColumn() > 5){
				resKingMove = kingMove[kingCoor.getRow()][kingCoor.getColumn()];
			} else if (quad == 3 && kingCoor.getRow() > 5 && kingCoor.getColumn() < 5) {
				resKingMove = kingMove[kingCoor.getRow()][kingCoor.getColumn()];
			} else if (quad == 4 && kingCoor.getRow() > 5 && kingCoor.getColumn() > 5) {
				resKingMove = kingMove[kingCoor.getRow()][kingCoor.getColumn()];
			}
		}
		
		return resKingMove;
		
	}
	
	/*Restituisce:
	 * 0 = nessun quadrante
	 * 1 = quadrante upLeft
	 * 2 = quadrante upRight 
	 * 3 = quadrante downLeft
	 * 4 = quadrante downRight
	 * */
	protected static int checkBlockEscape(State state) {
		
		int[][] upLeft = {{1,1},{1,2},{2,1}};
		int[][] upRight = {{1,6},{1,7},{2,7}};
		int[][] downLeft = {{6,1},{7,1},{7,2}};
		int[][] downRight = {{7,6},{7,7},{6,7}};
		
		int countUL = 0;
		int countUR = 0;
		int countDL = 0;
		int countDR = 0;
		
		int result = 0;
		
	    for (int[] position: upLeft){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())) {
	    		countUL++;
	        }
	    }
	    
	    for (int[] position: upRight){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())){
	    		countUR++;
	        }
	    }
	    
	    for (int[] position: downLeft){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())){
	    		countDL++;
	        }
	    }
	    
	    for (int[] position: downRight){
	    	if (state.getPawn(position[0],position[1]).equalsPawn(State.Pawn.BLACK.toString())){
	    		countDR++;
	        }
	    }
	    
	    if (countUL < 2 || countUR < 2 || countDL < 2 || countDR < 2) {
	    	
	    	int[] counts = {countUL, countUR, countDL, countDR};
	    	int v = 2;
	    	for (int i=0; i<4; i++) {
	    		if (counts[i] < v) {
	    			v = counts[i];
	    			result = i+1;
	    		}
	    	}
	    }
	    
	    return result;
	    
	}
	
	
		
	
	 
	 protected static double getBlackNearKing(State state, Coordinates kingCoord) {
		 int count=0;
		 
		
			
			if(state.getPawn(kingCoord.getRow()+1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow()-1, kingCoord.getColumn()).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()+1).equals(State.Pawn.BLACK))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()-1).equals(State.Pawn.BLACK))
				count++;
	
			return count;
	}
		
	protected static double getWhiteNearKing(State state, Coordinates kingCoord) {
			int count=0;
			
			if(kingCoord.getRow()!=4 && kingCoord.getColumn()!=4) {
			
			if(state.getPawn(kingCoord.getRow()+1, kingCoord.getColumn()).equals(State.Pawn.WHITE))
				count++;
			if(state.getPawn(kingCoord.getRow()-1, kingCoord.getColumn()).equals(State.Pawn.WHITE))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()+1).equals(State.Pawn.WHITE))
				count++;
			if(state.getPawn(kingCoord.getRow(), kingCoord.getColumn()-1).equals(State.Pawn.WHITE))
				count++;
			}
			
			return count;
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
	

   protected List<int[]> positionNearPawns(State state,Coordinates kingPos, String target){
       List<int[]> occupiedPosition = new ArrayList<int[]>();
       int[] pos = new int[2];
       //GET TURN
       State.Pawn[][] board = state.getBoard();
       if(board[kingPos.getRow()-1][kingPos.getColumn()].equalsPawn(target)) {
           pos[0] = kingPos.getRow() - 1;
           pos[1] = kingPos.getColumn();
           occupiedPosition.add(pos);
       }
       if(board[kingPos.getRow()+1][kingPos.getColumn()].equalsPawn(target)) {
           pos[0] = kingPos.getRow() + 1;
           occupiedPosition.add(pos);
       }
       if(board[kingPos.getRow()][kingPos.getColumn()-1].equalsPawn(target)) {
           pos[0] = kingPos.getRow();
           pos[1] = kingPos.getColumn() - 1;
           occupiedPosition.add(pos);
       }
       if(board[kingPos.getRow()][kingPos.getColumn()+1].equalsPawn(target)) {
           pos[0] = kingPos.getRow();
           pos[1] = kingPos.getColumn() + 1;
           occupiedPosition.add(pos);
       }

       return occupiedPosition;
   }
   

  public int getNumEatenPositions(State state){

      Coordinates kingPosition = state.getKingCords();

      if (kingPosition.getRow() == 4 && kingPosition.getColumn() == 4){
          return 4;
      } else if ((kingPosition.getRow() == 3 && kingPosition.getColumn() == 4) || (kingPosition.getRow() == 4 && kingPosition.getColumn() == 3)
                 || (kingPosition.getRow() == 5 && kingPosition.getColumn() == 4) || (kingPosition.getRow() == 4 && kingPosition.getColumn() == 5)){
          return 3;
      } else{
          return 2;
      }

  }
  

 public int checkNearPawns(State state, int[] position, String target){
     int count=0;
     //GET TURN
     State.Pawn[][] board = state.getBoard();
     if(board[position[0]-1][position[1]].equalsPawn(target))
         count++;
     if(board[position[0]+1][position[1]].equalsPawn(target))
         count++;
     if(board[position[0]][position[1]-1].equalsPawn(target))
         count++;
     if(board[position[0]][position[1]+1].equalsPawn(target))
         count++;
     return count;
 }

	
}
