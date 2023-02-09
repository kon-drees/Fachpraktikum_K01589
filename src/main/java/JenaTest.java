import de.nrw.hagen.fp1589.controller.ArgController;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.apache.jena.rdf.model.*;

import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JenaTest {
    static final String inputFileName  = "C:\\Apps\\GitRepos\\Fachpraktikum_K01589\\ArgSchlafstoerungen_arg.n3";
    static String personURI    = "http://somewhere/JohnSmith";
    static String fullName     = "John Smith";
    final static String argLocation1 = "Argbaum3.n3";
 String name = "Max Mustermann";

    public static void main(String[] args) {


        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);
        ArgController argController = new ArgController(argTreeEvaluator);
        //when
        argController.StartConversation();


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

    }



}
