public class Entity {

    private String label;
    private String iri;

    public Entity(String label, String iri) {
        this.label = label;
        this.iri = iri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }
}
