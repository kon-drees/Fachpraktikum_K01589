package de.nrw.hagen.fp1589.domain;

public class SchemaNode implements Node {

    private String claim;

    private String label;

    public SchemaNode(String label, String claim) {
        this.setLabel(label);
        this.claim = claim;
    }

    public SchemaNode() {

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
