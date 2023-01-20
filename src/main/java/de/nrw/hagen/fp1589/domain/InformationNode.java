package de.nrw.hagen.fp1589.domain;

import java.util.ArrayList;
import java.util.List;

public class InformationNode {

    private List<Triple> triples = new ArrayList<>();

    //Nur SchemaNodes erlaubt. Art der Verknuepfung ergibt sich aus SchemaNode.
    private List<SchemaNode> nodes;
    private int argStrenght = 1;

    private String claimText;

    public String getClaimText() {
        return this.claimText;
    }

    public void setClaimText(String claimText) {
        this.claimText = claimText;
    }

    public int getArgStrenght() {
        return argStrenght;
    }

    public void setArgStrenght(int argStrenght) {
        this.argStrenght = argStrenght;
    }




    public InformationNode(String subject, String predicate, String object) {
        this.triples.add(new Triple(subject, predicate, object));
    }

    public InformationNode() {

    }

    public void addTriple(Triple triple) {
        this.triples.add(triple);
    }

    public Triple getRiple(int index) {
        return this.triples.get(index);
    }

    public int getTripleSize() {
        return this.triples.size();
    }

}
