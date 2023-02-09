package de.nrw.hagen.fp1589.controller;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArgController {

    ArgTreeEvaluator argTreeEvaluator;

    public ArgController(ArgTreeEvaluator argTreeEvaluator) {
        this.argTreeEvaluator = argTreeEvaluator;
    }

    public void loadTree(ArgTree argTree){
        argTreeEvaluator.setArgTree(argTree);
    }

    // j n Questions
    public  List<InformationNode> askForArguments(){
      List<InformationNode>  nodesToAsk = argTreeEvaluator.getNodesForUser();
      List<InformationNode>  acceptedArguments = new ArrayList<>();
        for (InformationNode node:nodesToAsk) {
            for(int i = 0; i < node.getTripleSize(); i++){
                System.out.println(node.getTriple(i).getPredicate() +" "+ node.getTriple(i).getSubject()+ " "+
                        node.getTriple(i).getObject()+ "?");
            }
            System.out.println("ja oder nein?");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            if ("ja".equals(s.toLowerCase()))
                acceptedArguments.add(node);

        }

        return acceptedArguments;
    }

    public void showConclusion( ArrayList<InformationNode>  acceptedArguments){
        List<InformationNode> conlusions = argTreeEvaluator.getConclusionForUser(acceptedArguments);
        if (conlusions.isEmpty()){
            System.out.println("Keine Konklusion für die Argumente möglich!");
        }

            else{
            for (InformationNode node:conlusions) {
                for(int i = 0; i < node.getTripleSize(); i++){
                    System.out.println(node.getClaimText());
                }
        }

        }

    }

}
