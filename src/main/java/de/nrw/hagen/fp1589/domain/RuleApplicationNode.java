package de.nrw.hagen.fp1589.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
public class RuleApplicationNode extends SchemaNode{
    private final List<Node> premiseNodes = new ArrayList<>();

    private Node conclusionNode;

    private PreferenceApplicationNode preferredOf;

    private PreferenceApplicationNode dispreferredOf;

    //Hier nur CA-Nodes moeglich
    private ConflictApplicationNode conflictedOf;

    //hier CA-Node moeglich
    private ConflictApplicationNode conflictingOf;


    public List<Node> getPremiseNodes() {
        return this.premiseNodes;
    }


    @JsonIgnore
    public Node getConclusionNode() {
        return conclusionNode;
    }

    public void setConclusionNode(Node conclusionNode) {
        this.conclusionNode = conclusionNode;
    }

    public void addPremiseNode(Node node) {
        this.premiseNodes.add(node);
    }

    public void clearPremiseNodes() {
        this.premiseNodes.clear();
    }

    //Wenn der RA Node preferred ist, braucht der Baum nicht in das dispreferred Element laufen.
    @SuppressWarnings("GrazieInspection")
    @JsonIgnore
    public PreferenceApplicationNode getPreferredOf() {
        return this.preferredOf;
    }

    public void setPreferredOf(PreferenceApplicationNode preferredOf) {
        this.preferredOf = preferredOf;
    }

    public PreferenceApplicationNode getDispreferredOf() {
        return this.dispreferredOf;
    }

    public void setDispreferredOf(PreferenceApplicationNode dispreferredOf) {
        this.dispreferredOf = dispreferredOf;
    }

    public ConflictApplicationNode getConflictedOf() {
        return this.conflictedOf;
    }

    public void setConflictedOf(ConflictApplicationNode conflictedOf) {
        this.conflictedOf = conflictedOf;
    }

    public ConflictApplicationNode getConflictingOf() {
        return conflictingOf;
    }

    public void setConflictingOf(ConflictApplicationNode conflictingOf) {
        this.conflictingOf = conflictingOf;
    }
}
