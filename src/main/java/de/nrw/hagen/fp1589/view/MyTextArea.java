package de.nrw.hagen.fp1589.view;

import de.nrw.hagen.fp1589.domain.InformationNode;
import javafx.scene.control.TextArea;

public class MyTextArea extends TextArea {

    private InformationNode node;


    public MyTextArea(String text) {
        super (text);
    }

    public void setNode(InformationNode node) {
        this.node =node;
    }

    public InformationNode getNode(){
        return this.node;
    }


}
