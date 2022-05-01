package it.unibo.ai.didattica.competition.tablut.client;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.Heurisitcs.MyIterativeMinMax;
import it.unibo.ai.didattica.competition.tablut.domain.*;


public class TablutALFEClient extends TablutClient{

	public TablutALFEClient(String player, String name, int timeout, String ipAddress)
			throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
		// TODO Auto-generated constructor stub
	}

    public static void main(String[] args) throws IOException {

        String role = "";
        String name = "ALFE";
        String ipAddress = "localhost";
        int timeout = 60;

        if (args.length < 1) {
            System.out.println("You must specify which player you are (WHITE or BLACK)");
            System.out.println("USAGE: ./runmyplayer <black|white> <timeout-in-seconds> <server-ip> ");
            System.exit(-1);
        } else {
            role = (args[0]);
        }
        if (args.length == 2) {
            try {
                timeout = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("Timeout must be an integer representing seconds");
                System.out.println("USAGE: ./runmyplayer <black|white> <timeout-in-seconds> <server-ip>");
                System.exit(-1);
            }
        }
        if (args.length == 3) {
            try {
                timeout = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("Timeout must be an integer representing seconds");
                System.out.println("USAGE: ./runmyplayer <black|white> <timeout-in-seconds> <server-ip>");
                System.exit(-1);
            }
            ipAddress = args[2];
        }

        TablutALFEClient client = new TablutALFEClient(role, name, timeout, ipAddress);
        client.run();
}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 // send name of your group to the server saved in variable "name"
        try {
            this.declareName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // set type of state and WHITE must do the first player
        State state = new StateTablut();
        state.setTurn(State.Turn.WHITE);
        
        GameAshtonTablut tablutGame = new GameAshtonTablut(99, 0, "logs", "ALFEwhite", "ALFEblack");
        
        System.out.println("Player: " + (this.getPlayer().equals(State.Turn.BLACK) ? "BLACK" : "WHITE" ));
        System.out.println("Timeout: " + this.timeout +" s");
        System.out.println("Server: " + this.serverIp);
        
        while (true) {

            // update the current state from the server
            try {
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                e1.printStackTrace();
                System.exit(1);
            }

            // print current state
            System.out.println("Current state:");
            state = this.getCurrentState();
            System.out.println(state.toString());



            // if i'm WHITE
            if (this.getPlayer().equals(State.Turn.WHITE)) {

                // if is my turn (WHITE)
                if (state.getTurn().equals(StateTablut.Turn.WHITE)) {

                    System.out.println("\nSearching a suitable move... ");

                    // search the best move in search tree
                    Action a = findBestMove(tablutGame, state);

                    System.out.println("\nAction selected: " + a.toString());
                    try {
                        this.write(a);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }

                }

                // if is turn of oppenent (BLACK)
                else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
                    System.out.println("Waiting for your opponent move...\n");
                }
                // if I WIN
                else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                }
                // if I LOSE
                else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                }
                // if DRAW
                else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }

            }
            
            // if i'm BLACK
            else {

                // if is my turn (BLACK)
                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {

                    System.out.println("\nSearching a suitable move... ");

                    // search the best move in search tree
                    Action a = findBestMove(tablutGame, state);

                    System.out.println("\nAction selected: " + a.toString());
                    try {
                        this.write(a);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }

                }

                // if is turn of oppenent (WHITE)
                else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
                    System.out.println("Waiting for your opponent move...\n");
                }

                // if I LOSE
                else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                }

                // if I WIN
                else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                }

                // if DRAW
                else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }
            }
        }
        
	}


	private Action findBestMove(GameAshtonTablut tablutGame, State state) {
		// TODO Auto-generated method stub
        MyIterativeMinMax search = new MyIterativeMinMax(tablutGame, Double.MIN_VALUE, Double.MAX_VALUE, this.timeout - 2 );
        return search.makeDecision(state);		
	}

}