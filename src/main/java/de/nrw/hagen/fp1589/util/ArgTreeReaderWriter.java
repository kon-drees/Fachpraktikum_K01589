package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.ArgTree;

import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.RuleApplicationNode;
import de.nrw.hagen.fp1589.domain.Triple;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ArgTreeReaderWriter {






    public static ArgTree importTree(String fileLocation) {
        // create an empty model

        try {


            final FileReader fr = new FileReader("src/main/resources/" + fileLocation);

            String lastSubject = "";
            String type="";

            final HashMap<String, InformationNode> collectediNodes = new HashMap<>();
            final HashMap<String, Triple> collectedTriples = new HashMap<>();
            final HashMap<String, RuleApplicationNode> collectedRSA = new HashMap<>();
            final HashMap<String, String> jenaTriples = new HashMap();




            final Model model = ModelFactory.createDefaultModel();
            try {
                model.read(fr, null, "NTRIPLES");
            }
            catch (Exception ex) {
                System.out.println(" Fehler: " + ex.getMessage());
            }

            System.out.println("geklappt");

            StmtIterator iter = model.listStatements();

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
                        case "Statement" : collectedTriples.put(lastSubject, new Triple(jenaTriples.get("subject") , jenaTriples.get("predicate") , jenaTriples.get("object")));


                    }



                    lastSubject = subject.toString();
                    System.out.println("naechster Datensatz");
                    System.out.println(type);
                    jenaTriples.clear();
                }
                if (predicate.getLocalName().equals("type")) {
                    type = object.asResource().getLocalName();
                }
                jenaTriples.put(predicate.getLocalName(), object.toString());


                System.out.print(subject.toString());
                System.out.print(" " + predicate.getLocalName() + " ");
                if (object instanceof Resource) {
                    System.out.print(object.toString());
                } else {
                    // object is a literal
                    System.out.print(" \"" + object.toString() + "\"");
                }

                System.out.println(" .");
            }
            fr.close();

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

        // read the RDF/XML file
        return null;

    }


    public static void printTree(ArgTree tree) {
        //RDFDataMgr.write(System.out, this.importedModel, RDFFormat.NT) ;
    }

}
