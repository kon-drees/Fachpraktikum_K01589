package de.nrw.hagen.fp1589.domain;



public class ConflictApplicationNode extends SchemaNode {
    private Node conflictingNode;

    private Node conflictedNode;

    public Node getConflictingNode() {
        return this.conflictingNode;
    }

    public void setConflictingNode(Node conflictingNode) {
        this.conflictingNode = conflictingNode;
    }
}
