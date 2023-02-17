package de.nrw.hagen.fp1589.domain;

@SuppressWarnings("unused")
public class SchemaNode implements Node {

    private String claim;

    private String label;

    @SuppressWarnings("unused")
    public SchemaNode(String label, String claim) {
        this.setLabel(label);
        this.claim = claim;
    }

    public SchemaNode() {

    }


    public String getClaim() {
        return claim;
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
