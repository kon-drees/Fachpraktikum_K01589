package de.nrw.hagen.fp1589.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class PreferenceApplicationNode extends SchemaNode{


    /*
    Als PreferredNode werden nur RuleApplicationNodes beruecksichtigt.
    Wegen der temporaeren Zuweisung eines "EmptyNode" ist allerdings der Basistyp erforderlich.
     */
    private Node preferredNode;

    private Node dispreferredNode;


    public Node getPreferredNode() {
        return this.preferredNode;
    }

    public void setPreferredNode(Node preferredNode) {
        this.preferredNode = preferredNode;
    }

    //Kein Ruecklauf in den Dispreferred RA Knoten (Da kommt man her, Folge: Zirkel)
    @JsonIgnore
    public Node getDispreferredNode() {
        return this.dispreferredNode;
    }

    public void setDisPreferredNode(Node dispreferredNode) {
        this.dispreferredNode = dispreferredNode;
    }
}
