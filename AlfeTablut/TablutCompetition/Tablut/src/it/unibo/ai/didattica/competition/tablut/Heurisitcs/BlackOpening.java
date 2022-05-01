package it.unibo.ai.didattica.competition.tablut.Heurisitcs;

import java.io.IOException;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

/**
 * @author L.Piazza
 *
 */
public class BlackOpening{


	public Action openingMove(State currentState) {
		/*Se in apertura ho possibilita' di mangiare ai bordi lo faccio*/
		Action a=controllaMangiata(currentState);
		if(a!=null)
			return a;

		/*Altrimenti guardo prima se il bianco ha aperto muovendo le pedine interne piu' vicine al Re*/
		if(currentState.getPawn(3,4).equalsPawn("O") )
			try {
				return new Action("e2", "g2", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(4,5).equalsPawn("O"))	
			try {
				return new Action("h5", "h7", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(5,4).equalsPawn("O"))	
			try {
				return new Action("e8", "c8", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(4,3).equalsPawn("O"))	
			try {
				return new Action("b5", "b7", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		/*Guardo ora se invece il bianco ha aperto muovendo una delle pedine più esterne*/
		else if(currentState.getPawn(2,4).equalsPawn("O")) {
				try {
					return new Action("e2", "g2", Turn.BLACK);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(currentState.getPawn(4,6).equalsPawn("O")) {
				try {
					return new Action("h5", "h7", Turn.BLACK);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(currentState.getPawn(6,4).equalsPawn("O")) {
				try {
					return new Action("e8", "c8", Turn.BLACK);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(currentState.getPawn(4,2).equalsPawn("O")) {
				try {
					return new Action("b5", "b7", Turn.BLACK);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		/*Never returned*/
		return null;		
	}
	
	private Action controllaMangiata(State currentState) {
		if(currentState.getPawn(2,0).equalsPawn("W"))
			try {
				return new Action("e2", "a2", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(0,2).equalsPawn("W"))
			try {
				return new Action("b5", "b1", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(0,6).equalsPawn("W"))
			try {
				return new Action("h5", "h1", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(2,8).equalsPawn("W"))
			try {
				return new Action("e2", "i2", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(6,8).equalsPawn("W"))
			try {
				return new Action("e8", "i8", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(8,6).equalsPawn("W"))
			try {
				return new Action("h5", "h9", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(8,2).equalsPawn("W"))
			try {
				return new Action("b5", "b9", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(currentState.getPawn(6,0).equalsPawn("W"))
			try {
				return new Action("e8", "a8", Turn.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
