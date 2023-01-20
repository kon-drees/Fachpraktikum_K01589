package de.nrw.hagen.fp1589.util;

import de.nrw.hagen.fp1589.domain.ArgTree;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.FileReader;
import java.io.InputStream;



public class ArgTreeReaderWriter {






    public static ArgTree importTree(String fileLocation) {
        // create an empty model

        try {


            FileReader fr = new FileReader("src/main/resources/" + fileLocation);


            Model model = ModelFactory.createDefaultModel();
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

                System.out.print(subject.toString());
                System.out.print(" " + predicate.toString() + " ");
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
