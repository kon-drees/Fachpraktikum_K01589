package de.nrw.hagen.fp1589.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InformationNode implements Node {

    private List<Triple> triples = new ArrayList<>();

    private List<RuleApplicationNode>  premiseOf = new ArrayList<>();

    private List<RuleApplicationNode> conclusionOf = new ArrayList<>();

    private ConflictApplicationNode conflictingOf;

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

    @JsonIgnore
    public Triple getTriple(int index) {
        return this.triples.get(index);
    }

    public int getTripleSize() {
        return this.triples.size();
    }

    @JsonIgnore

    public SchemaNode getPremiseOf(int index) {
        return this.premiseOf.get(index);
    }

    public List<RuleApplicationNode>   getPremiseOf() {
        return this.premiseOf;
    }


    @JsonIgnore
    public List<InformationNode> getPremisesOfNodeList() {
        List<InformationNode> premiseList = new ArrayList<>();
        for (RuleApplicationNode raNode : premiseOf){
            List<Node> premiseOfList = raNode.getPremiseNodes();
            for (Node premise:premiseOfList) {
                premiseList.add((InformationNode) premise);
            }
        }
        return premiseList;
    }

    @JsonIgnore
    public List<InformationNode> getConclusionsOfNodeList() {
        List<InformationNode> conclusionList = new ArrayList<>();
        for (RuleApplicationNode raNode : conclusionOf){
            conclusionList.add((InformationNode) raNode.getPremiseNodes());
        }
        return conclusionList;
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


    //public Iterator<RuleApplicationNode> getPremiseOfNodes() {
        //return this.premiseOf.iterator();
    //}


    //Normalerweise nur conclusion eines RA-Nodes, oder auch mehrere moeglich?
    public Iterator<RuleApplicationNode> getConclusionOfNodes() {
        return this.conclusionOf.iterator();
    }

    public ConflictApplicationNode getConflictingOf() {
        return this.conflictingOf;
    }

    public void setConflictingOf(ConflictApplicationNode conflictingOf) {
        this.conflictingOf = conflictingOf;
    }
}
