package de.nrw.hagen.fp1589.domain;
import java.util.List;
public class RuleApplicationNode extends SchemaNode{
    private List<Node> premiseNodes;

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
}
