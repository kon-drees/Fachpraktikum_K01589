package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.Node;
import de.nrw.hagen.fp1589.domain.RuleApplicationNode;

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
        Iterator<InformationNode> rootIterator = argTree.getInformationNodes();
        LinkedList<Node> nodeToCheckSet = new LinkedList<>();

        //gets the premisses of the root Nodes
        while (rootIterator.hasNext()) {
            InformationNode node = rootIterator.next();
            Iterator<RuleApplicationNode> premissesIterator = node.getConclusionOfNodes();
            while (premissesIterator.hasNext()) {
                List<Node> premissesNodesList = premissesIterator.next().getPremiseNodes();
                for (Node premisses : premissesNodesList) {
                    if (!nodeToCheckSet.contains(premisses)) {
                        nodeToCheckSet.add(premisses);
                    }
                }

            }

        }
        // gets the premises of the nodes and checks if there are exists no  duplicates
        while (!nodeToCheckSet.isEmpty()) {
            InformationNode nodeToCheck = (InformationNode) nodeToCheckSet.pop();
            Iterator<RuleApplicationNode> premissesIterator = nodeToCheck.getConclusionOfNodes();
            if (premissesIterator != null && !lastNodesList.contains(nodeToCheck)) {
                lastNodesList.add(nodeToCheck);
            } else {
                while (premissesIterator.hasNext()) {
                    List<Node> premissesNodesList = premissesIterator.next().getPremiseNodes();
                    for (Node premisses : premissesNodesList) {
                        if (!nodeToCheckSet.contains(premisses) && !lastNodesList.contains(premisses)) {
                            nodeToCheckSet.add(premisses);
                        }
                    }
                    nodeToCheckSet.addAll(premissesIterator.next().getPremiseNodes());
                }
            }

        }
        return lastNodesList;


    }


    public List<InformationNode> getNodesForUser() {
        List<InformationNode> lastNodesList = new ArrayList<>();
        lastNodesList = getLastNodes();
        evaluatedNodes.addAll(lastNodesList);

        return lastNodesList;
    }


    public List<InformationNode> getConclusionForUser(List<InformationNode> argumentList) {
        List<InformationNode> conclusionList = new ArrayList<>();
        conclusionList = evaluateArguments(argumentList);
        return conclusionList;

    }


    // input of the users chosen and relevant arguments
    // returns the conclusion of the arguments
    private List<InformationNode> evaluateArguments(List<InformationNode> argumentList) {
        List<InformationNode> conclusionList = new ArrayList<>();
        if (argumentationList == null) {
            throw new RuntimeException("No Arguments");
        }
        if (argumentList.isEmpty())
            return conclusionList;

        //INode nicht aus dem Baum!
        if (argumentList.get(0).getPremiseOf().size() == 0) {
            argumentList = this.searchPremiseINodes(argumentList);
        }
        Iterator<InformationNode> rootIterator = argTree.getInformationNodes();
        while (rootIterator.hasNext()) {
            InformationNode node = rootIterator.next();
        }

        for (Node inputObj : argumentList) {
            InformationNode inputArg = (InformationNode) inputObj;
            List<RuleApplicationNode> ruleApplicationNodeList = inputArg.getPremiseOf();
            for (RuleApplicationNode node :
                    ruleApplicationNodeList) {
                if (!node.getPremiseNodes().isEmpty() && argumentList.containsAll(node.getPremiseNodes()) ) {
                    if (!conclusionList.contains(node.getConclusionNode()))
                        conclusionList.add((InformationNode) node.getConclusionNode());

                }
            }
        }
        return conclusionList;
    }


    public List<InformationNode> getConclusionsForArgument(InformationNode argument){
        List<InformationNode> conclusionList = new ArrayList<>();
        for (RuleApplicationNode ruleApplicationNode :  argument.getPremiseOf()) {
            conclusionList.add((InformationNode) ruleApplicationNode.getConclusionNode()) ;
        }


        return conclusionList;
    }

    private List<InformationNode> searchPremiseINodes(List<InformationNode> inodes) {
        int i = 0;
        boolean gefunden = false;
        InformationNode testinode = null;
        List<InformationNode> foundINodes = new ArrayList<>();
        List<InformationNode> searchinINodes = this.getNodesForUser();
        if (searchinINodes == null || searchinINodes.size() == 0) {
            return null;
        }
        for (InformationNode inode : inodes) {
            if (inode.getPremiseOf().size() > 0) {
                foundINodes.add(inode);
                continue;
            }
            gefunden = false;
            while ( i < searchinINodes.size() ) {
                testinode = searchinINodes.get(i++);
                if (testinode.equals(inode)) {
                    gefunden = true;
                    break;
                }
            }

            if (gefunden) {
                foundINodes.add(testinode);
            }
        }

        return foundINodes;
    }


}
