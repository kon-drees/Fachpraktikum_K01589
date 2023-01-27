package de.nrw.hagen.fp1589.domain;

public class EmptyNode implements Node{
    private String label;

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public EmptyNode(String label){
        this.setLabel(label);
    }
}
