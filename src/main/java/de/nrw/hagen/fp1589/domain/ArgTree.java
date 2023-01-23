package de.nrw.hagen.fp1589.domain;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ArgTree {
    private List<InformationNode> inodes;

    public ArgTree() {
        this.inodes = new ArrayList<>();
    }
    public void addINode(InformationNode inode) {
        //unsicher, besser mit Kopierkonstruktor
        this.inodes.add(inode);
    }

    public int getSize() {
        return this.inodes.size();
    }

    public Iterator<InformationNode> getEnumeration() {
        return this.inodes.iterator();
    }

    public InformationNode getInformationNode(int index) {
        return this.inodes.get(index);
    }
}