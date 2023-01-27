package de.nrw.hagen.fp1589.util.impl;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;


@RunWith(JUnit4ClassRunner.class)
public class ArgTreeReaderWriterTest {
    @Test
    public void testRead() {



        ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        //ArgTree tree = ArgTreeReaderWriter.importTree("calcifediol_arg.n3");

        assertTrue(tree != null);
        assertTrue(tree.getSize() == 11);
        assertTrue(tree.getInformationNode(0).getLabel().equals("Arg11"));
        assertTrue(tree.getInformationNode(0).getPremiseOf(0).getLabel().equals("RuleApplicationNodeImpl1"));


    }


}
