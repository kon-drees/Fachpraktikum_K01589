package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.Node;
import de.nrw.hagen.fp1589.domain.RuleApplicationNode;

import java.util.*;


/**
 * Evaluator of an argument tree
 */
@SuppressWarnings({"SlowListContainsAll", "MismatchedQueryAndUpdateOfCollection", "SuspiciousMethodCalls", "DuplicatedCode", "DataFlowIssue"})
public class ArgTreeEvaluator {
    private ArgTree argTree;
    private final List<String> argumentationList = new ArrayList<>();
    private final List<InformationNode> evaluatedNodes = new ArrayList<>(); // saves the references to already evaluated nodes


    public ArgTreeEvaluator(ArgTree argTree) {
        this.argTree = argTree;
    }

    @SuppressWarnings("unused")
    public ArgTree getArgTree() {
        return argTree;
    }

    public void setArgTree(ArgTree argTree) {
        this.argTree = argTree;
    }


    //LOOK CONCLUSIONS OR PREMISE

    //last nodes of a tree don't have any premisses .
    // so we iterate through all nodes and check if a node doesn't have any premisses more specifically no conclusion of a node

    /**
     * Returns the last information nodes to the input argument tree.
     * <p>
     *
     * @return InformationNode List
     */
    private List<InformationNode> getLastNodes() {
        List<InformationNode> lastNodesList = new ArrayList<>();
        Iterator<InformationNode> rootIterator = argTree.getInformationNodes();
        LinkedList<Node> nodeToCheckSet = new LinkedList<>();


        //gets the premisses of the root Nodes
        while (rootIterator.hasNext()) {
            InformationNode node = rootIterator.next();
            Iterator<RuleApplicationNode> premissesIterator = node.getConclusionOfNodes();
            while (premissesIterator.hasNext()) {
                RuleApplicationNode ruleApplicationNode = premissesIterator.next();
                List<Node> premissesNodesList = ruleApplicationNode.getPremiseNodes();

                // Check for conflicted single argument without a root and

                if(ruleApplicationNode.getConflictedOf() != null){
                   Node nodeToCheck = ruleApplicationNode.getConflictedOf().getConflictingNode();
                   if (nodeToCheck instanceof InformationNode){
                       nodeToCheckSet.add(nodeToCheck);
                   }


                }


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
                while (Objects.requireNonNull(premissesIterator).hasNext()) {
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


    /**
     * Returns the last information nodes to the input argument tree.
     * <p>
     *
     * @return InformationNode List
     */
    public List<InformationNode> getNodesForUser() {
        List<InformationNode> lastNodesList;
        lastNodesList = getLastNodes();
        evaluatedNodes.addAll(lastNodesList);

        return lastNodesList;
    }


    public List<InformationNode> getConclusionForUser(List<InformationNode> argumentList) {
        List<InformationNode> conclusionList;
        conclusionList = evaluateArguments(argumentList);
        return conclusionList;

    }


    // input of the users chosen and relevant arguments
    // returns the conclusion of the arguments
    private List<InformationNode> evaluateArguments(List<InformationNode> argumentList) {
        List<InformationNode> conclusionList = new ArrayList<>();
        //noinspection ConstantValue
        if (argumentationList == null) {
            throw new RuntimeException("No Arguments");
        }
        if (argumentList.isEmpty())
            return conclusionList;

        //INode nicht aus dem Baum!
        if (argumentList.get(0).getPremiseOf().size() == 0) {
            argumentList = this.searchPremiseINodes(argumentList);
        }


        for (InformationNode inputObj : argumentList) {

            List<RuleApplicationNode> ruleApplicationNodeList = inputObj.getPremiseOf();
            for (RuleApplicationNode node :
                    ruleApplicationNodeList) {
                if (!node.getPremiseNodes().isEmpty() && argumentList.containsAll(node.getPremiseNodes()) ) {

                    long premArgStrength = 0;
                    long confArgStrength = 0;
                    // get the strength of the premisses
                    List<Node> nodeList = node.getPremiseNodes();
                    for (Node iNode:nodeList
                         ) {
                        InformationNode iNodeA = (InformationNode) iNode;
                        premArgStrength =  premArgStrength + iNodeA.getArgStrength();
                    }


                    //checks for conflicted
                    //checks for isolated argument
                    if(node.getConflictedOf() != null){
                        Node nodeToCheck = node.getConflictedOf().getConflictingNode();
                        if (nodeToCheck instanceof InformationNode){
                            confArgStrength = confArgStrength +  ((InformationNode) nodeToCheck).getArgStrength();
                        }
                        if (nodeToCheck instanceof RuleApplicationNode){
                            List<Node> conflictedNodes = ((RuleApplicationNode) nodeToCheck).getPremiseNodes();
                            if (argumentList.containsAll(conflictedNodes)){
                                for (Node conflictedNode :
                                        conflictedNodes) {

                                    InformationNode iNode = (InformationNode) conflictedNode;
                                    if(argumentList.contains(iNode))
                                        confArgStrength =  confArgStrength + iNode.getArgStrength();
                                }
                            }
                        }
                        if(premArgStrength >= confArgStrength )
                            if (!conclusionList.contains(node.getConclusionNode()))
                                conclusionList.add((InformationNode) node.getConclusionNode());
                    }

                   else if(node.getConflictingOf() != null){
                        Node nodeToCheck = node.getConflictingOf().getConflictedNode();
                        if (nodeToCheck instanceof InformationNode){
                            confArgStrength = confArgStrength +  ((InformationNode) nodeToCheck).getArgStrength();
                        }
                        if (nodeToCheck instanceof RuleApplicationNode){
                            List<Node> conflictedNodes = ((RuleApplicationNode) nodeToCheck).getPremiseNodes();
                            if (argumentList.containsAll(conflictedNodes)){
                                for (Node conflictedNode :
                                        conflictedNodes) {
                                    InformationNode iNode = (InformationNode) conflictedNode;
                                    if(argumentList.contains(iNode))
                                        confArgStrength =  confArgStrength + iNode.getArgStrength();
                                }
                            }
                        }
                        if(premArgStrength >= confArgStrength )
                            if (!conclusionList.contains(node.getConclusionNode()))
                                conclusionList.add((InformationNode) node.getConclusionNode());
                    }


                    //checks if preferred exist
                   else if (node.getDispreferredOf() != null){
                            RuleApplicationNode preferedNode = (RuleApplicationNode) node.getDispreferredOf().getPreferredNode();
                            if(argumentList.containsAll(preferedNode.getPremiseNodes()) && !conclusionList.contains(node.getConclusionNode()))
                                conclusionList.add((InformationNode) node.getConclusionNode());


                    }

                   else if (node.getPreferredOf() != null){
                        RuleApplicationNode disPreferedNode = (RuleApplicationNode) node.getPreferredOf().getDispreferredNode();
                        if(!argumentList.containsAll(disPreferedNode.getPremiseNodes()) && !conclusionList.contains(node.getConclusionNode()))
                            conclusionList.add((InformationNode) node.getConclusionNode());


                    }

                    else {
                        if (argumentList.containsAll(node.getPremiseNodes()) && !conclusionList.contains((InformationNode) node.getConclusionNode()))
                            conclusionList.add((InformationNode) node.getConclusionNode());
                    }
                }
            }
        }
        return conclusionList;
    }


    /**
     * get the conclusion of the input argument of the tree
     * <p>
     *
     * @param argument Input Argument
     * @return List InformationNode of the conclusion to the input argument
     */
    public List<InformationNode> getConclusionsForArgument(InformationNode argument){
        List<InformationNode> conclusionList = new ArrayList<>();
        for (RuleApplicationNode ruleApplicationNode :  argument.getPremiseOf()) {
            conclusionList.add((InformationNode) ruleApplicationNode.getConclusionNode()) ;
        }


        return conclusionList;
    }

    private List<InformationNode> searchPremiseINodes(List<InformationNode> inodes) {
        int i = 0;
        boolean gefunden;
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
