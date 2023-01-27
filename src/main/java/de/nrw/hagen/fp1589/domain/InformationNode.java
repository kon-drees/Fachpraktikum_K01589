package de.nrw.hagen.fp1589.domain;

import java.util.ArrayList;
import java.util.List;

public class InformationNode implements Node {

    private List<Triple> triples = new ArrayList<>();

    private List<RuleApplicationNode>  premiseOf = new ArrayList<>();

    private List<RuleApplicationNode> conclusionOf = new ArrayList<>();

    //Nur SchemaNodes erlaubt. Art der Verknuepfung ergibt sich aus SchemaNode.
    private List<SchemaNode> nodes;
    private long argStrength = 1;

    private String claimText;

    private String label;

    private String source;

    public String getClaimText() {
        return this.claimText;
    }

    public void setClaimText(String claimText) {
        this.claimText = claimText;
    }

    public long getArgStrength() {
        return argStrength;
    }

    public void setArgStrength(long argStrenght) {
        this.argStrength = argStrenght;
    }




    public InformationNode(String subject, String predicate, String object) {
        this.triples.add(new Triple(subject, predicate, object));
    }

    public InformationNode(Triple triple) {
        this.triples.add(triple);
    }

    public InformationNode() {

    }

    public void addTriple(Triple triple) {
        this.triples.add(triple);
    }

    public void addPremiseOf(RuleApplicationNode raNode) {
        this.premiseOf.add(raNode);
    }

    public void addConclusionOf(RuleApplicationNode raNode) {
        this.conclusionOf.add(raNode);
    }

    public Triple getTriple(int index) {
        return this.triples.get(index);
    }

    public int getTripleSize() {
        return this.triples.size();
    }

    public SchemaNode getPremiseOf(int index) {
        return this.premiseOf.get(index);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
