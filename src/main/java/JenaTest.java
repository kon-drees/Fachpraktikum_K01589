import de.nrw.hagen.fp1589.controller.ArgController;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;


public class JenaTest {

    final static String argLocation1 = "Argbaum2.n3";


    public static void main(String[] args) {


        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);
        ArgController argController = new ArgController(argTreeEvaluator);
        //when
        argController.StartConversation();

    }







}
