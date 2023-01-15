public class SchemaNode extends Node {
    private Triple triple;
    private String claim;

    public SchemaNode(String label, Triple triple, String claim) {
        super(label);
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
}
