package de.nrw.hagen.fp1589.util.impl;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.Node;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


@SuppressWarnings("deprecation")
@RunWith(JUnit4ClassRunner.class)
public class ArgTreeEvaluatorTest {


    final String argLocation1 = "Argbaum1.n3";
    final String argLocation2 = "Argbaum6.n3";


    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testGetLastNodesOne() {


        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();

        //should
        Iterator<InformationNode> rootIterator = argTree != null ? argTree.getInformationNodes() : null;
        Node nodeOne = rootIterator.next().getConclusionOfNodes().next().getPremiseNodes().get(0);
        Node nodeTwo = rootIterator.next().getConclusionOfNodes().next().getPremiseNodes().get(0);


        assertEquals(nodeOne, (nodes.get(0)));
        assertEquals(nodeTwo, (nodes.get(1)));


    }


    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testGetLastNodesTwo() {


        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation2);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();

        //should
        Iterator<InformationNode> rootIterator = null;
        if (argTree != null) {
            rootIterator = argTree.getInformationNodes();
        }
        List<Node> nodesList = rootIterator.next().getConclusionOfNodes().next().getPremiseNodes();



        assertEquals(nodesList.get(0), (nodes.get(0)));
        assertEquals(nodesList.get(1), (nodes.get(1)));


    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testGetConclusionForUserOne() {
        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();
        List<InformationNode> conclusionList = argTreeEvaluator.getConclusionForUser(nodes);

        //should
        Iterator<InformationNode> rootIterator = null;
        if (argTree != null) {
            rootIterator = argTree.getInformationNodes();
        }
        InformationNode conclusionOne = rootIterator.next();
        InformationNode conclusionTwo = rootIterator.next();


        assertEquals(conclusionOne, (conclusionList.get(0)));
        assertEquals(conclusionTwo, (conclusionList.get(1)));

    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testGetConclusionForUserTwo() {
        //is
        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();
        nodes.remove(1);
        List<InformationNode> conclusionList = argTreeEvaluator.getConclusionForUser(nodes);

        //should
        Iterator<InformationNode> rootIterator = null;
        if (argTree != null) {
            rootIterator = argTree.getInformationNodes();
        }
        InformationNode conclusionOne = rootIterator.next();
        InformationNode conclusionTwo = rootIterator.next();


        assertEquals(conclusionOne, (conclusionList.get(0)));
        assertNotEquals(conclusionTwo, (conclusionList.get(0)));

    }

    @Test
    public void testGetReasoning() {


        ArgTree argTree = ArgTreeReaderWriter.importTree(argLocation1);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(argTree);

        //when
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();
        List<InformationNode> conclusionList = argTreeEvaluator.getConclusionsForArgument(nodes.get(0));
        conclusionList.addAll(argTreeEvaluator.getConclusionsForArgument(nodes.get(1)));
        //should
        assert argTree != null;
        Iterator<InformationNode> rootIterator = argTree.getInformationNodes();
        InformationNode conclusionOne = rootIterator.next();
        InformationNode conclusionTwo = rootIterator.next();


        assertEquals(conclusionOne, (conclusionList.get(0)));
        assertEquals(conclusionTwo, (conclusionList.get(1)));




    }


}
