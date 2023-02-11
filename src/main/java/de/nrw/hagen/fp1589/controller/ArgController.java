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

    public void loadTree(ArgTree argTree) {
        argTreeEvaluator.setArgTree(argTree);
    }

    public void StartConversation(){
        List<InformationNode> nodes =  askForArguments();
        showConclusion((ArrayList<InformationNode>) nodes);
    }

    // j n Questions
    public List<InformationNode> askForArguments() {
        List<InformationNode> nodesToAsk = argTreeEvaluator.getNodesForUser();
        List<InformationNode> acceptedArguments = new ArrayList<>();
        int j = 0;
        while( j != nodesToAsk.size()) {
            InformationNode node = nodesToAsk.get(j);
            for (int i = 0; i < node.getTripleSize(); i++) {
                System.out.println(node.getTriple(i).getPredicate() + " " + node.getTriple(i).getSubject() + " " +
                        node.getTriple(i).getObject() + "?");
            }
            System.out.println("ja oder nein oder warum?");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            if ("ja".equals(s.toLowerCase())){
                acceptedArguments.add(node);
                j++;
            }
            else if ("warum".equals(s.toLowerCase()))
                showReasoning(node);
            else
                j++;

        }

        return acceptedArguments;
    }

    public void showConclusion(ArrayList<InformationNode> acceptedArguments) {
        List<InformationNode> conlusions = argTreeEvaluator.getConclusionForUser(acceptedArguments);
        if (conlusions.isEmpty()) {
            System.out.println("Keine Konklusion für die Argumente möglich!");
        } else {
            for (InformationNode node : conlusions) {
                for (int i = 0; i < node.getTripleSize(); i++) {
                    System.out.println(node.getClaimText());
                }
            }

        }

    }

    public void showReasoning(InformationNode argument){

        List<InformationNode> reasonsList =  argTreeEvaluator.getConclusionsForArgument(argument);
        for (InformationNode node:reasonsList
             ) {
            System.out.println(node.getClaimText());
        }
    }

    public void evaluateConclusion(ArrayList<InformationNode> acceptedArguments){

    }

}
