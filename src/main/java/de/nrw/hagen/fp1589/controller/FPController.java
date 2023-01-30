package de.nrw.hagen.fp1589.controller;

public class FPController {
    public static void main(String[] args) {
        String filename = "";
        int requestNumber = 0;
        if (args.length < 4) {
            System.out.println("fehlerhafte Anzahl an Argumenten");
        }

        switch (args[0]) {
            //Filename
            case "-f" :
                filename = args[1];
                //Input request (Anfrage)
            case "-i":
                requestNumber = Integer.parseInt(args[1]);
        }

        switch (args[2]) {
            //Filename
            case "-f" :
                filename = args[3];
                //Input request (Anfrage)
            case "-i":
                requestNumber = Integer.parseInt(args[3]);
        }




    }
}
