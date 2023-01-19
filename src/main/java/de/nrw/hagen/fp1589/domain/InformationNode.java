package de.nrw.hagen.fp1589.domain;

import java.util.List;

public class InformationNode {

    private Entity subject;
    private Predicate predicate;
    private Entity object;
    //Nur SchemaNodes erlaubt. Art der Verknuepfung ergibt sich aus SchemaNode.
    private List<SchemaNode> nodes;
    private int argStrenght = 1;


    public int getArgStrenght() {
        return argStrenght;
    }

    public void setArgStrenght(int argStrenght) {
        this.argStrenght = argStrenght;
    }


     // Metadata


    public InformationNode(Entity subject, Predicate predicate, Entity object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Entity getSubject() {
        return subject;
    }

    public void setSubject(Entity subject) {
        this.subject = subject;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public Entity getObject() {
        return object;
    }

    public void setObject(Entity object) {
        this.object = object;
    }
}
