package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


@SuppressWarnings("HttpUrlsUsage")
public class ArgTreeReaderWriter {

    public static ArgTree importTree(String fileLocation) {

        final HashMap<String, InformationNode> collectediNodes = new HashMap<>();
        final HashMap<String, Triple> collectedTriples = new HashMap<>();
        final HashMap<String, RuleApplicationNode> collectedRA = new HashMap<>();
        final HashMap<String, PreferenceApplicationNode> collectedPA = new HashMap<>();
        final HashMap<String, ConflictApplicationNode> collectedCA = new HashMap<>();

        try {
            final FileReader fr = new FileReader("src/main/resources/" + fileLocation);

            String lastSubject = "";  //Einfacher Gruppenwechsel. lastsubject solange gleich, bis naechster Node anfaengt.
            String type="";

            final HashMap<String, String> jenaTriples = new HashMap<>();  //Jede Zeile der n3-Datei sammeln, bis node vollstaendig eingelesen ist
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
                if (!lastSubject.equals(subject.toString())) {  //Gruppenwechsel: Naechster node beginnt, bisher eingelesene jenatriples auswerten und Node erzeugen
                    collectNode(type, lastSubject, jenaTriples, collectedCA , collectedPA, collectedRA, collectedTriples, collectediNodes);
                    lastSubject = subject.toString();
                    jenaTriples.clear();
                    i = 0;
                }

                //type merken, anhand dessen wird entschieden, welcher NodeType angelegt wird.
                if ("type".equals(predicate.getLocalName())) {
                    type = object.asResource().getLocalName();
                }
                if ("argStrength".equals(predicate.getLocalName())) {
                    jenaTriples.put("argStrength", String.valueOf(object.asLiteral().getLong()));
                } else {
                    if (jenaTriples.containsKey(predicate.getLocalName())) {  //Falls Attribut mehrfach vorkommt, z.B. premise, dann wird durchnummeriert
                        i++;
                        jenaTriples.put(predicate.getLocalName() + i, object.toString());
                    } else {
                        jenaTriples.put(predicate.getLocalName(), object.toString());
                    }
                }
            }
            //Letzten Node nicht vergessen.
            collectNode(type, lastSubject, jenaTriples, collectedCA , collectedPA, collectedRA, collectedTriples, collectediNodes);
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
        ArgTree tree = createTree(collectediNodes,collectedRA, collectedTriples, collectedPA, collectedCA);  //Nodes miteinander verbinden.
        tree.setName(fileLocation);  //Um Baum zureuck speichern zu koennen merkt er sich seinen Namen.
        return tree;
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

    private static void collectNode(String type, String subject, final HashMap<String, String> jenaTriples, final HashMap<String, ConflictApplicationNode> collectedCA ,
                                    final HashMap<String, PreferenceApplicationNode> collectedPA , final HashMap<String, RuleApplicationNode> collectedRA , final HashMap<String, Triple> collectedTriples, final HashMap<String, InformationNode> collectediNodes ) {

        switch (type) {
            case "Statement" ->
                    collectedTriples.put(subject, new Triple(jenaTriples.get("subject"), jenaTriples.get("predicate"), jenaTriples.get("object")));
            case "I-node" -> {
                InformationNode inode = new InformationNode();
                inode.setLabel(subject);
                inode.setArgStrength(Long.parseLong(jenaTriples.get("argStrength")));
                inode.setSource(jenaTriples.get("source"));
                inode.setClaimText(jenaTriples.get("claimText"));
                collectediNodes.put(subject, inode);
            }
            case "RA-node" -> {
                RuleApplicationNode raNode = new RuleApplicationNode();
                raNode.setLabel(subject);
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
                collectedRA.put(subject, raNode);
            }
            case "PA-node" -> {
                PreferenceApplicationNode paNode = new PreferenceApplicationNode();
                paNode.setLabel(subject);
                if (jenaTriples.containsKey("Preferred")) {
                    paNode.setPreferredNode(new EmptyNode(jenaTriples.get("Preferred")));
                }
                if (jenaTriples.containsKey("Dispreferred")) {
                    paNode.setDisPreferredNode(new EmptyNode(jenaTriples.get("Dispreferred")));
                }
                collectedPA.put(subject, paNode);
            }
            case "CA-node" -> {
                ConflictApplicationNode caNode = new ConflictApplicationNode();
                caNode.setLabel(subject);
                if (jenaTriples.containsKey("Conflicted")) {
                    caNode.setConflictedNode(new EmptyNode(jenaTriples.get("Conflicted")));
                }
                if (jenaTriples.containsKey("Conflicting")) {
                    caNode.setConflictingNode(new EmptyNode(jenaTriples.get("Conflicting")));
                }
                collectedCA.put(subject, caNode);
            }
        }
    }



    public static void writeTree(String fileLocation, ArgTree tree) throws Exception {
        final FileWriter fw = new FileWriter("src/main/resources/" + fileLocation);

        final Model model = ModelFactory.createDefaultModel();
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


    public static void main(String[] args) throws Exception {
        ArgTree tree = ArgTreeReaderWriter.importTree("Argbaum5.n3");

        if (tree != null) {
            ArgTreeReaderWriter.writeTree("test1.n3" , tree);
        }


    }
}
