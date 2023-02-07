package de.nrw.hagen.fp1589.util.impl;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(JUnit4ClassRunner.class)
public class ArgTreeReaderWriterTest {
    @Test
    public void testRead() {



        ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        //ArgTree tree = ArgTreeReaderWriter.importTree("calcifediol_arg.n3");


        ArgTreeEvaluator eva = new ArgTreeEvaluator(tree);

        ArrayList<InformationNode> args = new ArrayList<>();


        InformationNode start = tree.getInformationNode(0);

        System.out.println(start.getConclusionOfNodes().next().getPremiseNodes().size());


        args.add(tree.getInformationNode(0));
        System.out.println("rufe evaluator auf");

        List<InformationNode> ergebnisse = eva.getConclusionForUser(start.getConclusionOfNodes().next().getPremiseNodes());

        System.out.println(ergebnisse.size());


        for (InformationNode ergnode : ergebnisse) {
            System.out.println(ergnode.getLabel() + " : " + ergnode.getClaimText());
        }




        assertTrue(tree != null);
        assertTrue(tree.getSize() == 2);
        assertTrue(tree.getInformationNode(0).getLabel().equals("Arg6"));
        //assertTrue(tree.getInformationNode(0).getPremiseOf(0).getLabel().equals("RuleApplicationNodeImpl1"));


    }


}
