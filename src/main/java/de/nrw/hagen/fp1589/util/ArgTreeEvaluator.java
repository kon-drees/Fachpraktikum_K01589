package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;

import java.util.*;

public class ArgTreeEvaluator {


    private ArgTree argTree;
    private List<String> argumentationList = new ArrayList<>();
    private List<InformationNode> evaluatedNodes = new ArrayList<>(); // saves the references to already evaluated nodes


    public ArgTreeEvaluator(ArgTree argTree) {
        this.argTree = argTree;
    }

    public ArgTree getArgTree() {
        return argTree;
    }

    public void setArgTree(ArgTree argTree) {
        this.argTree = argTree;
    }


    //LOOK CONCLUSIONS OR PREMISE

    //last nodes of a tree don't have any premisses .
    //so we iterate through all nodes and check if a node doesn't have any premisses more specifically no conclusion of a node
    private List<InformationNode> getLastNodes() {
        List<InformationNode> lastNodesList = new ArrayList<>();
        Iterator<InformationNode> nodeIterator = argTree.getInformationNodes();
        while (nodeIterator.hasNext()) {
            InformationNode node = nodeIterator.next();
            //check if premiseList is empty and doesn't contain in the already evaluated list
            if (node.getConclusionsOfNodeList().isEmpty() && !evaluatedNodes.contains(node))
                lastNodesList.add(node);
        }

        // when no last nodes + conclusion !!!
       // if (lastNodesList.isEmpty()) ;


        return lastNodesList;
    }


    public List<InformationNode> getNodesForUser() {
        List<InformationNode> lastNodesList = new ArrayList<>();
        lastNodesList = getLastNodes();
        evaluatedNodes.addAll(lastNodesList);

        return lastNodesList;
    }


    public List<InformationNode> getConclusionForUser(ArrayList<InformationNode> argumentList) {

        List<InformationNode> conclusionList = new ArrayList<>();
        conclusionList = evaluateArguments(argumentList);
        return conclusionList;

    }


    // input of the users chosen and relevant arguments
    // returns the conclusion of the arguments
    private List<InformationNode> evaluateArguments(ArrayList<InformationNode> argumentList) {
        List<InformationNode> conclusionList = new ArrayList<>();
        if (argumentationList == null) {
            throw new RuntimeException("No Arguments");
        }

        if (argumentList.isEmpty())
            return conclusionList;

        for (InformationNode inputArg : argumentList) {
            List<InformationNode> premiseOfList = inputArg.getPremisesOfNodeList();
            if (premiseOfList.isEmpty())
                return conclusionList;
            for (InformationNode conclusion : premiseOfList) {
                if (!conclusionList.contains(conclusion)) {
                    List<InformationNode> conclusionOfList = conclusion.getConclusionsOfNodeList();
                    boolean isConclusion = argumentList.containsAll(conclusionOfList);
                    if (isConclusion)
                        conclusionList.add(conclusion);

                }

            }


        }

        //Conclusion can be Empty
        return conclusionList;

    }





}
