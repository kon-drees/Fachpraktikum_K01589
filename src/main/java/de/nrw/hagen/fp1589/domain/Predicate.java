package de.nrw.hagen.fp1589.domain;

public class Predicate {

    private String label;
    private String iri;
    private String schema;

    public Predicate(String label, String iri, String schema) {
        this.label = label;
        this.iri = iri;
        this.schema = schema;
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

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
