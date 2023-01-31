package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.*;

import org.apache.jena.rdf.model.*;

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
        final HashMap<String, RuleApplicationNode> collectedRSA = new HashMap<>();

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

            System.out.println("geklappt");

            StmtIterator iter = model.listStatements();
            int i = 0;

// print out the predicate, subject and object of each statement
            while (iter.hasNext()) {
                Statement stmt      = iter.nextStatement();  // get next statement
                Resource subject   = stmt.getSubject();     // get the subject
                Property  predicate = stmt.getPredicate();   // get the predicate
                RDFNode   object    = stmt.getObject();      // get the object

                if ("".equals(lastSubject)) {
                    lastSubject = subject.toString();
                }
                if (!lastSubject.equals(subject.toString())) {
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
                                System.out.println("-----");
                                raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise")));
                                System.out.println(jenaTriples.get("Premise"));
                                int j = 1;
                                while (jenaTriples.containsKey("Premise" + j)) {
                                    raNode.addPremiseNode(new EmptyNode(jenaTriples.get("Premise" + j)));
                                    System.out.println(jenaTriples.get("Premise" + j));
                                    j++;
                                }
                            }
                            if (jenaTriples.containsKey("Conclusion")) {
                                raNode.setConclusionNode(new EmptyNode(jenaTriples.get("Conclusion")));
                            }
                            collectedRSA.put(lastSubject, raNode);
                        }
                    }

                    lastSubject = subject.toString();
                    //System.out.println("naechster Datensatz");
                    //System.out.println(type);
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
                case "RA-Node" -> {
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
                    collectedRSA.put(lastSubject, raNode);
                }
            }
            fr.close();

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("inodes:" + collectediNodes.size());
        System.out.println("statements:" + collectedTriples.size());
        System.out.println("ranodes:" + collectedRSA.size());


        return createTree(collectediNodes,collectedRSA, collectedTriples);

    }


    public static void printTree(ArgTree tree) {
        //RDFDataMgr.write(System.out, this.importedModel, RDFFormat.NT) ;
    }

    private static ArgTree createTree(Map<String, InformationNode> iNodes, Map<String, RuleApplicationNode> raNodes , Map<String, Triple> triples) {
        ArgTree tree = new ArgTree();
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
        return tree;
    }

}
