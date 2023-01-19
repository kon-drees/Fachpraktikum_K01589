package de.nrw.hagen.fp1589.domain;



public class PreferenceApplicationNode extends SchemaNode{

    private Node preferredNodes;

    private Node dispreferredNode;


    public Node getPreferredNodes() {
        return this.preferredNodes;
    }

    public void setPreferredNodes(Node preferredNodes) {
        this.preferredNodes = preferredNodes;
    }
}
