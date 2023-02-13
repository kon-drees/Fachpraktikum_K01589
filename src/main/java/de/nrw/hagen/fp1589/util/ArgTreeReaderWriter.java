package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.rdf.model.impl.StatementImpl;
import org.apache.jena.riot.thrift.wire.RDF_BNode;
import org.apache.jena.vocabulary.RDF;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


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
                if (!lastSubject.equals(subject.toString())) {
                    //System.out.println("type: " + type);
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



    public static void writeTree(String fileLocation, ArgTree tree) throws Exception {
        final FileWriter fw = new FileWriter("src/main/resources/" + fileLocation);
        final HashMap<String, InformationNode> collectediNodes = new HashMap<>();
        final HashMap<String, Triple> collectedTriples = new HashMap<>();
        final HashMap<String, RuleApplicationNode> collectedRA = new HashMap<>();
        final HashMap<String, PreferenceApplicationNode> collectedPA = new HashMap<>();
        final HashMap<String, ConflictApplicationNode> collectedCA = new HashMap<>();

        final Model model = ModelFactory.createDefaultModel();

        int i = 0;
        for (Iterator<InformationNode> it = tree.getInformationNodes(); it.hasNext(); ) {
            InformationNode node = it.next();
            //createInformationNode(model, node);
            for (Iterator<RuleApplicationNode> iter = node.getConclusionOfNodes(); iter.hasNext(); ) {
                RuleApplicationNode ra = iter.next();
                for (Node inode : ra.getPremiseNodes()) {
                    createInformationNode(model, (InformationNode)  inode);
                }
                createInformationNode(model, (InformationNode) ra.getConclusionNode());
                createRuleApplicationNode(model, ra);
                if (ra.getConflictedOf() != null) {
                    createConflictApplicationNode(model , ra.getConflictedOf());
                }
                if(ra.getPreferredOf() != null) {
                    createPreferenceApplicationNode(model, ra.getPreferredOf());
                }
            }
        }

        model.write(fw,  "NTRIPLES");



    }

    private static ReifiedStatement createJenaTriple(Model model, Triple triple) {
        var object = model.getResource(triple.getObject().replace(" " , "_"));
        var subject = model.getResource(triple.getSubject().replace(" " , "_"));
        var property = model.createProperty(triple.getPredicate().replace(" " , "_"));
        var statement = model.createStatement(subject, property, object);
        return statement.createReifiedStatement();
    }

    private static void createInformationNode(Model model, InformationNode node) {

        var resource = model.createResource(node.getLabel());
        if (model.contains(resource, RDF.type))
            return;
        resource.addProperty(RDF.type, model.createResource("http://www.arg.dundee.ac.uk/aif#I-node"));
        resource.addLiteral(model.createProperty("http://www.arg.dundee.ac.uk/aif#claimText"), node.getClaimText());
        resource.addLiteral(model.createProperty("http://www.example.org/aif#argStrength"), node.getArgStrength());

        resource.addProperty(model.createProperty("http://www.example.org/aif#source"), createJenaTriple(model, node.getTriple(0)));
    }

    private static void createRuleApplicationNode(Model model, RuleApplicationNode node) {

        var resource = model.createResource(node.getLabel());
        if (model.contains(resource, RDF.type))
            return;

        resource.addProperty(RDF.type, model.createResource("http://www.arg.dundee.ac.uk/aif#RA-node"));

        var conclusionNode = model.getResource(node.getConclusionNode().getLabel());
        resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Conclusion"), conclusionNode);

        for(Node premnode : node.getPremiseNodes()) {
            resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Premise"), model.getResource(premnode.getLabel()));
        }

    }

    private static void createConflictApplicationNode(Model model, ConflictApplicationNode node) {
        var resource = model.createResource(node.getLabel());
        if (model.contains(resource, RDF.type))
            return;
        if (node.getConflictingNode() instanceof InformationNode) {
            createInformationNode(model, (InformationNode) node.getConflictingNode());
        }
        else {
            createRuleApplicationNode(model, (RuleApplicationNode) node.getConflictingNode());
        }
        resource.addProperty(RDF.type, model.createResource("http://www.arg.dundee.ac.uk/aif#CA-node"));
        var conflictedNode = model.getResource(node.getConflictedNode().getLabel());
        resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Conflicted"), conflictedNode);
        var conflictingNode = model.getResource(node.getConflictingNode().getLabel());
        resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Conflicting"), conflictingNode);

    }

    private static void createPreferenceApplicationNode(Model model, PreferenceApplicationNode node) {
        var resource = model.createResource(node.getLabel());
        if (model.contains(resource, RDF.type))
            return;
        resource.addProperty(RDF.type, model.createResource("http://www.arg.dundee.ac.uk/aif#PA-node"));
        var preferredNode = model.getResource(node.getPreferredNode().getLabel());
        resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Preferred"), preferredNode);
        var dispreferredNode = model.getResource(node.getDispreferredNode().getLabel());
        resource.addProperty(model.createProperty("http://www.arg.dundee.ac.uk/aif#Dispreferred"), dispreferredNode);

    }


    public static void main(String args[]) throws Exception {
        ArgTree tree = ArgTreeReaderWriter.importTree("Argbaum5.n3");

        ArgTreeReaderWriter.writeTree("test1.n3" , tree);


    }
}
