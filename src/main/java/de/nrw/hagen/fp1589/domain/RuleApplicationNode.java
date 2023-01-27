package de.nrw.hagen.fp1589.domain;
import java.util.ArrayList;
import java.util.List;
public class RuleApplicationNode extends SchemaNode{
    private List<Node> premiseNodes = new ArrayList<>();

    private Node conclusionNode;

    //dirty, aber schnell :-)
    public List<Node> getPremiseNodes() {
        return this.premiseNodes;
    }



    public void setPremiseNodes(List<Node> premiseNodes) {
        this.premiseNodes = premiseNodes;
    }

    public Node getConclusionNode() {
        return conclusionNode;
    }

    public void setConclusionNode(Node conclusionNode) {
        this.conclusionNode = conclusionNode;
    }

    public void addPremiseNode(Node node) {
        this.premiseNodes.add(node);
    }

    public void clearPremiseNodes() {
        this.premiseNodes.clear();;
    }

}
