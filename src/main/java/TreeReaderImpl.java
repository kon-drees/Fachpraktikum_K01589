import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.*;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class TreeReaderImpl implements TreeReader {

    private Model importedModel;
    private String fileLocation;


    @Override
    public void importTree() {
        // create an empty model
        this.importedModel = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open( fileLocation );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + fileLocation + " not found");
        }

        // read the RDF/XML file
        this.importedModel.read(in, "", "N3");

    }

    public Model getImportedModel() {
        return importedModel;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public void printTree() {

        RDFDataMgr.write(System.out, this.importedModel, RDFFormat.NT) ;

    }
}
