package de.nrw.hagen.fp1589.util.impl;
import de.nrw.hagen.fp1589.controller.ArgController;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(JUnit4ClassRunner.class)
public class ArgTreeReaderWriterTest {
    @Test
    public void testRead() {



        ArgTree tree = ArgTreeReaderWriter.importTree("Argbaum1.n3");
        //ArgTree tree = ArgTreeReaderWriter.importTree("calcifediol_arg.n3");


        System.out.println("-------------");
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(tree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();
        System.out.println(nodes.size());
        System.out.println(nodes.get(0).getLabel());
        System.out.println(nodes.get(1).getLabel());

        List<InformationNode> conclusionList = argTreeEvaluator.getConclusionForUser(nodes);
        System.out.println(conclusionList.size());
        System.out.println(conclusionList.get(0).getLabel());
        System.out.println(conclusionList.get(1).getLabel());
        //should
        Iterator<InformationNode> rootIterator = tree.getInformationNodes();
        InformationNode conclusionOne = rootIterator.next();
        InformationNode conclusionTwo = rootIterator.next();





        assertTrue(tree != null);
        assertTrue(tree.getSize() == 2);
        //assertTrue(tree.getInformationNode(0).getLabel().equals("Arg6"));
        //assertTrue(tree.getInformationNode(0).getPremiseOf(0).getLabel().equals("RuleApplicationNodeImpl1"));


    }


}
