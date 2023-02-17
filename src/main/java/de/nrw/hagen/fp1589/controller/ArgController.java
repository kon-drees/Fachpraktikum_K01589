package de.nrw.hagen.fp1589.controller;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller for the Argumentation Tree Evaluator
 */
public class ArgController {



    final ArgTreeEvaluator argTreeEvaluator;

    /**
     * Constructor
     * <p>
     *
     * @param argTreeEvaluator
     */
    @SuppressWarnings("JavadocDeclaration")
    public ArgController(ArgTreeEvaluator argTreeEvaluator) {
        this.argTreeEvaluator = argTreeEvaluator;
    }

    @SuppressWarnings("unused")
    public void loadTree(ArgTree argTree) {
        argTreeEvaluator.setArgTree(argTree);
    }

    /**
     * Starts the conversation for a potential chatbot
     */
    public void StartConversation(){
        List<InformationNode> nodes =  askForArguments();
        showConclusion((ArrayList<InformationNode>) nodes);
    }

    public void startVisualConversation(){
        List<InformationNode> nodes =  askForArgumentsVisual();
        showConclusionVisual((ArrayList<InformationNode>) nodes);
    }


    /**
     * Program asks via console, which arguments apply to the user
     * <p>
     *
     * @return InformationNode List.
     */
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
            if ("ja".equalsIgnoreCase(s)){
                acceptedArguments.add(node);
                j++;
            }
            else if ("warum".equalsIgnoreCase(s))
                showReasoning(node);
            else
                j++;
        }
        return acceptedArguments;
    }

    public List<InformationNode> askForArgumentsVisual() {
        List<InformationNode> nodesToAsk = argTreeEvaluator.getNodesForUser();
        List<InformationNode> acceptedArguments = new ArrayList<>();
        int j = 0;
        while( j != nodesToAsk.size()) {
            InformationNode node = nodesToAsk.get(j);
            if (node.getConflictingOf()!= null) {
                j++;
                continue;
            }

            int reply = JOptionPane.showConfirmDialog(null, node.getTriple(0).getPredicate() + " " +
                    node.getTriple(0).getSubject() + "\n" + node.getTriple(0).getObject() + "?" , "Premise erfüllt?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION){
                acceptedArguments.add(node);
            }
            j++;
        }
        return acceptedArguments;
    }

    /**
     * Calculates the Conclusion depending on the Arguments of the user
     *
     * @param acceptedArguments Array of the input Arguments
     */
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


    public void showConclusionVisual(ArrayList<InformationNode> acceptedArguments) {
        StringBuilder ausgabe = new StringBuilder();
        List<InformationNode> conlusions = argTreeEvaluator.getConclusionForUser(acceptedArguments);
        if (conlusions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Keine Konklusion für die Argumente möglich!");
        } else {
            for (InformationNode node : conlusions) {
                for (int i = 0; i < node.getTripleSize(); i++) {
                    ausgabe.append(node.getClaimText()).append("\n\n");
                }
            }
            JOptionPane.showMessageDialog(null, ausgabe.toString());
        }
    }


    /**
     *
     * Shows the reasoning (Conclusion) to the input Argument
     *
     * @param argument Input argument
     */
    public void showReasoning(InformationNode argument){

        List<InformationNode> reasonsList =  argTreeEvaluator.getConclusionsForArgument(argument);
        for (InformationNode node:reasonsList
             ) {
            System.out.println("Klärung von\n" + argument.getClaimText() + "\nkann auf\n" + node.getClaimText() + "\nschließen");
        }
    }



}
