package de.nrw.hagen.fp1589.domain;

public class Triple {
    private String subject;
    private String predicate;
    private String object;

    public String getSubject() {
        return this.subject.replaceAll("_", " ");
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return this.predicate.replaceAll("_", " ");
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getObject() {
        return this.object.replaceAll("_", " ");
    }

    public void setObject(String object) {
        this.object = object;
    }


    public Triple() {

    }
    public Triple(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }
}
