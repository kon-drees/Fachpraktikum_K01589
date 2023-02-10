package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.*;

import org.apache.jena.rdf.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class ArgTreeReaderWriter {

    public static ArgTree importTree(String fileLocation) {
        // create an empty model
        final HashMap<String, InformationNode> collectediNodes = new HashMap<>();
        final HashMap<String, Triple> collectedTriples = new HashMap<>();
        final HashMap<String, RuleApplicationNode> collectedRA = new HashMap<>();
        final HashMap<String, PreferenceApplicationNode> collectedPA = new HashMap<>();
        final HashMap<String, ConflictApplicationNode> collectedCA = new HashMap<>();

        try {


            final FileReader fr = new FileReader("src/main/resources/" + fileLocation);

            String lastSubject = "";
            String type="";


            final HashMap<String, String> jenaTriples = new HashMap<>();

            final Model model = ModelFactory.createDefaultModel();
            try {
                model.read(fr, null, "NTRIPLES");
            }
            catch (Exception ex) {
                System.out.println(" Fehler: " + ex.getMessage());
            }


            StmtIterator iter = model.listStatements();
            int i = 0;


            while (iter.hasNext()) {
                Statement stmt      = iter.nextStatement();  // get next statement
                Resource subject   = stmt.getSubject();     // get the subject
                Property  predicate = stmt.getPredicate();   // get the predicate
                RDFNode   object    = stmt.getObject();      // get the object

                if ("".equals(lastSubject)) {
                    lastSubject = subject.toString();
                }
                //System.out.println("lastsubject:" + lastSubject);
                if (!lastSubject.equals(subject.toString())) {
                    switch (type) {
                        case "Statement" ->
                                collectedTriples.put(lastSubject, new Triple(jenaTriples.get("subject"), jenaTriples.get("predicate"), jenaTriples.get("object")));
                        case "I-node" -> {
                            InformationNode inode = new InformationNode();
                            inode.setLabel(lastSubject);
                            if (jenaTriples.get("argStrength") != null) {
                                inode.setArgStrength(Long.parseLong(jenaTriples.get("argStrength")));
                            }
                            inode.setSource(jenaTriples.get("source"));
                            inode.setClaimText(jenaTriples.get("claimText"));
                            collectediNodes.put(lastSubject, inode);
                        }
                        case "RA-node" -> {
                            RuleApplicationNode raNode = new RuleApplicationNode();
                            raNode.setLabel(lastSubject);
                            if (jenaTriples.containsKey("Premise")) {
                                raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise")));
                                int j = 1;
                                while (jenaTriples.containsKey("Premise" + j)) {
                                    raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise" + j)));
                                    j++;
                                }
                            }
                            if (jenaTriples.containsKey("Conclusion")) {
                                raNode.setConclusionNode(new EmptyNode(jenaTriples.get("Conclusion")));
                            }
                            collectedRA.put(lastSubject, raNode);
                        }
                        case "PA-node" -> {
                            PreferenceApplicationNode paNode = new PreferenceApplicationNode();
                            paNode.setLabel(lastSubject);
                            if (jenaTriples.containsKey("Preferred")) {
                                paNode.setPreferredNode(new EmptyNode(jenaTriples.get("Preferred")));
                            }
                            if (jenaTriples.containsKey("Dispreferred")) {
                                paNode.setDisPreferredNode(new EmptyNode(jenaTriples.get("Dispreferred")));
                            }
                            collectedPA.put(lastSubject, paNode);
                        }
                        case "CA-node" -> {
                            ConflictApplicationNode caNode = new ConflictApplicationNode();
                            caNode.setLabel(lastSubject);
                            if (jenaTriples.containsKey("Conflicted")) {
                                caNode.setConflictedNode(new EmptyNode(jenaTriples.get("Conflicted")));
                            }
                            if (jenaTriples.containsKey("Conflicting")) {
                                caNode.setConflictingNode(new EmptyNode(jenaTriples.get("Conflicting")));
                            }
                            collectedCA.put(lastSubject, caNode);
                        }
                    }
                    lastSubject = subject.toString();
                    jenaTriples.clear();
                    i = 0;
                }



                if ("type".equals(predicate.getLocalName())) {
                    type = object.asResource().getLocalName();
                }
                if ("argStrength".equals(predicate.getLocalName())) {
                    jenaTriples.put("argStrength", String.valueOf(object.asLiteral().getLong()));
                } else {
                    if (jenaTriples.containsKey(predicate.getLocalName())) {
                        i++;
                        jenaTriples.put(predicate.getLocalName() + i, object.toString());
                    } else {
                        jenaTriples.put(predicate.getLocalName(), object.toString());
                    }
                }

                //System.out.print(subject.toString());
                //System.out.print(" " + predicate.getLocalName() + " ");
                /*
                if (object instanceof Resource) {
                    //System.out.print(object.toString());
                } else {
                    // object is a literal
                    //System.out.print(" \"" + object.toString() + "\"");
                }
*/

            }


            switch (type) {
                case "Statement" ->
                        collectedTriples.put(lastSubject, new Triple(jenaTriples.get("subject"), jenaTriples.get("predicate"), jenaTriples.get("object")));
                case "I-node" -> {
                    InformationNode inode = new InformationNode();
                    inode.setLabel(lastSubject);
                    inode.setArgStrength(Long.parseLong(jenaTriples.get("argStrength")));
                    inode.setSource(jenaTriples.get("source"));
                    inode.setClaimText(jenaTriples.get("claimText"));
                    collectediNodes.put(lastSubject, inode);
                }
                case "RA-node" -> {
                    RuleApplicationNode raNode = new RuleApplicationNode();
                    raNode.setLabel(lastSubject);
                    if (jenaTriples.containsKey("Premise")) {
                        raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise")));
                        int j = 1;
                        while (jenaTriples.containsKey("Premise" + j)) {
                            raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise" + j)));
                            j++;
                        }
                    }
                    if (jenaTriples.containsKey("Conclusion")) {
                        raNode.setConclusionNode(new EmptyNode(jenaTriples.get("Conclusion")));
                    }
                    collectedRA.put(lastSubject, raNode);
                }
                case "PA-node" -> {
                    PreferenceApplicationNode paNode = new PreferenceApplicationNode();
                    paNode.setLabel(lastSubject);
                    if (jenaTriples.containsKey("Preferred")) {
                        paNode.setPreferredNode(new EmptyNode(jenaTriples.get("Preferred")));
                    }
                    if (jenaTriples.containsKey("Dispreferred")) {
                        paNode.setDisPreferredNode(new EmptyNode(jenaTriples.get("Dispreferred")));
                    }
                    collectedPA.put(lastSubject, paNode);
                }
                case "CA-node" -> {
                    ConflictApplicationNode caNode = new ConflictApplicationNode();
                    caNode.setLabel(lastSubject);
                    if (jenaTriples.containsKey("Conflicted")) {
                        caNode.setConflictedNode(new EmptyNode(jenaTriples.get("Conflicted")));
                    }
                    if (jenaTriples.containsKey("Conflicting")) {
                        caNode.setConflictingNode(new EmptyNode(jenaTriples.get("Conflicting")));
                    }
                    collectedCA.put(lastSubject, caNode);
                }
            }
            fr.close();

        }

        catch (FileNotFoundException ex) {
            System.out.println("Datei nicht gefunden");
            ex.printStackTrace();
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("inodes:" + collectediNodes.size());
        System.out.println("statements:" + collectedTriples.size());
        System.out.println("ranodes:" + collectedRA.size());


        return createTree(collectediNodes,collectedRA, collectedTriples, collectedPA, collectedCA);

    }


    public static void printTree(ArgTree tree) {
        //RDFDataMgr.write(System.out, this.importedModel, RDFFormat.NT) ;
    }

    private static ArgTree createTree(Map<String, InformationNode> iNodes, Map<String, RuleApplicationNode> raNodes , Map<String, Triple> triples , Map<String, PreferenceApplicationNode> paNodes, Map<String, ConflictApplicationNode> caNodes) {
        final ArgTree tree = new ArgTree();
        final List<String> zwischen = new ArrayList<>();
        for (String id : iNodes.keySet()) {
            InformationNode iNode = iNodes.get(id);
            if (iNode.getSource() != null) {
                Triple triple = triples.get(iNode.getSource());
                iNode.addTriple(triple);
            }
        }


        for (String id: raNodes.keySet()) {
            RuleApplicationNode raNode = raNodes.get(id);
            if (raNode.getConclusionNode() != null) {
                InformationNode iNode = iNodes.get(raNode.getConclusionNode().getLabel());
                iNode.addConclusionOf(raNode);
                tree.addINode(iNode);
                raNode.setConclusionNode(iNode);
            }
            if (raNode.getPremiseNodes() != null) {
                for (Node eNode : raNode.getPremiseNodes()) {
                    zwischen.add(eNode.getLabel());
                }
                raNode.clearPremiseNodes();
                for (String nodeid : zwischen) {
                    InformationNode iNode = iNodes.get(nodeid);
                    iNode.addPremiseOf(raNode);

                    raNode.addPremiseNode(iNode);
                }
                zwischen.clear();
            }
        }

        for (String id: paNodes.keySet()) {
            PreferenceApplicationNode paNode = paNodes.get(id);
            if (paNode.getPreferredNode() != null) {  // Hier werden nur Referenzen auf RA-Nodes beruecksichtigt. !!!
                RuleApplicationNode raNode = raNodes.get(paNode.getPreferredNode().getLabel());
                raNode.setPreferredOf(paNode);
                paNode.setPreferredNode(raNode);
            }
            if (paNode.getDispreferredNode() != null) {
                RuleApplicationNode raNode = raNodes.get(paNode.getDispreferredNode().getLabel());
                raNode.setDispreferredOf(paNode);
                paNode.setDisPreferredNode(raNode);
            }
        }

        for (String id: caNodes.keySet()) {
            ConflictApplicationNode caNode = caNodes.get(id);
            if (caNode.getConflictedNode() != null) {  // Hier werden nur Referenzen auf RA-Nodes beruecksichtigt. !!!
                System.out.println("conflicted by" + caNode.getConflictedNode().getLabel());
                RuleApplicationNode raNode = raNodes.get(caNode.getConflictedNode().getLabel());
                raNode.setConflictedOf(caNode);
                caNode.setConflictedNode(raNode);
            }
            if (caNode.getConflictingNode() != null) { //Hier I-Node und RA-Node moeglich
                Node confNode = caNode.getConflictingNode();
                if (confNode.getLabel().startsWith("Rule")) {
                    RuleApplicationNode raNode = raNodes.get(confNode.getLabel());
                    raNode.setConflictingOf(caNode);
                    caNode.setConflictingNode(raNode);
                }
                else {  //ansonsten nur I-Node moeglich
                    InformationNode iNode = iNodes.get(confNode.getLabel());
                    iNode.setConflictingOf(caNode);
                    caNode.setConflictingNode(iNode);
                }
            }
        }
        return tree;
    }
}
