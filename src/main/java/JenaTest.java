import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

public class JenaTest {
    static final String inputFileName  = "C:\\Apps\\GitRepos\\Fachpraktikum_K01589\\ArgSchlafstoerungen_arg.n3";
    static String personURI    = "http://somewhere/JohnSmith";
    static String fullName     = "John Smith";

 String name = "Max Mustermann";

    public static void main(String[] args) {
        // some definitions
        String personURI    = "http://somewhere/JohnSmith";
        String givenName    = "John";
        String familyName   = "Smith";
        String fullName     = givenName + " " + familyName;

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();



        // create the resource
        //   and add the properties cascading style
        Resource johnSmith
                = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

        // list the statements in the Model
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object    = stmt.getObject();      // get the object

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

        model.write(System.out);



        fileImportTest();

    }



    public static void fileImportTest()
    {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, "", "N3");

        // write it to standard out
        model.write(System.out, "N3");
        org.apache.jena.riot.RDFWriter.source(model).set(RIOT.symTurtleDirectiveStyle, "N3")
                .lang(Lang.TTL)
                .output(System.out);


        RDFDataMgr.write(System.out, model, RDFFormat.NT) ;
    }



}
