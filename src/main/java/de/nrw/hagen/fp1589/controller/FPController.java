package de.nrw.hagen.fp1589.controller;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;

@SuppressWarnings("UnusedAssignment")
public class FPController {
    @SuppressWarnings("unused")
    public static void main(String[] args) {

        ArgTree argTree;
        if (args.length < 1) {
            argTree = ArgTreeReaderWriter.importTree("Argbaum1.n3");

        }
        else {
            argTree = ArgTreeReaderWriter.importTree("args[0]");
        }

        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);
        ArgController argController = new ArgController(argTreeEvaluator);
        //when
        argController.StartConversation();




    }
}
