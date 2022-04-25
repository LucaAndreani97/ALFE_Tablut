package it.unibo.ai.didattica.competition.tablut.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class TablutALFEWhiteClient {
    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
        System.out.println("TablutALFEWhiteClient");
        String[] array = new String[]{"WHITE"};
        if (args.length > 1){
            array = new String[]{"WHITE", args[0], args[1]};
        } else {
            System.err.println("Not enought arguments!");
            System.exit(1);
        }
        TablutALFEClient.main(array);
    }
}
