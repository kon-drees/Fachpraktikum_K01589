public class SchemaNode implements Node {
    private Triple triple;
    private String claim;

    private String label;

    public SchemaNode(String label, Triple triple, String claim) {
        this.setLabel(label);
        this.triple = triple;
        this.claim = claim;
    }

    public Triple getTriple() {
        return triple;
    }

    public void setTriple(Triple triple) {
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
