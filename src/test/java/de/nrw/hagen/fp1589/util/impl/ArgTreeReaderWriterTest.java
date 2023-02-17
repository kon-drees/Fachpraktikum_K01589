package de.nrw.hagen.fp1589.util.impl;
import de.nrw.hagen.fp1589.domain.ArgTree;

import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;



@SuppressWarnings("deprecation")
@RunWith(JUnit4ClassRunner.class)
public class ArgTreeReaderWriterTest {
    @Test
    public void testRead() {
        ArgTree tree = ArgTreeReaderWriter.importTree("Argbaum1.n3");
        assertNotNull(tree);
        assertEquals(2, tree.getSize());
        assertThat(tree.getInformationNode(0).getLabel(),
                CoreMatchers.either(CoreMatchers.is("Arg5")).or(CoreMatchers.is("Arg10")));
    }


}
