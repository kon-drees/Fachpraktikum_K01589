package de.nrw.hagen.fp1589.domain;

public class SchemaNode implements Node {
    private InformationNode triple;
    private String claim;

    private String label;

    public SchemaNode(String label, InformationNode triple, String claim) {
        this.setLabel(label);
        this.triple = triple;
        this.claim = claim;
    }

    public SchemaNode() {

    }

    public InformationNode getTriple() {
        return triple;
    }

    public void setTriple(InformationNode triple) {
        this.triple = triple;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    @Override
    public String getLabel() {
        return this.label ;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }
}
