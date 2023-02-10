package de.nrw.hagen.fp1589.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.service.ArgTreeJson;
import de.nrw.hagen.fp1589.service.MemoryService;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ArgTreeRestController {

    private String tree;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @GetMapping("/getpremises")
    @ResponseStatus(HttpStatus.OK)
    public ArgTreeJson getpremises() {
        //ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        ArgTree tree = ArgTreeReaderWriter.importTree(this.tree);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(tree);
        List<InformationNode> nodes = argTreeEvaluator.getNodesForUser();
        return new ArgTreeJson(nodes);
    }


    @PutMapping("/getconclusions")
    @ResponseStatus(HttpStatus.OK)
    public ArgTreeJson getconclusions(@RequestBody ArgTreeJson inodes) throws Exception{
        //ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        System.out.println("getconclusions aufgerufen");
        //List<InformationNode> nodes = Collections.singletonList(jacksonObjectMapper.readValue(premises, InformationNode.class));
        //InformationNode inode = jacksonObjectMapper.readValue(premises, InformationNode.class);
        System.out.println(inodes.nodes().size());
        InformationNode inode = inodes.nodes().get(0);
        System.out.println("inode:" + inode.getLabel() + " " + inode.getTriples().get(0).getPredicate());
        ArgTree tree = ArgTreeReaderWriter.importTree(this.tree);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(tree);

        inodes.nodes().remove(0);

        List<InformationNode> testnodes = argTreeEvaluator.getConclusionForUser(inodes.nodes());

        return new ArgTreeJson(testnodes);
    }


    @GetMapping("/settree")
    public void setTree(@RequestParam String tree) {
        this.tree = tree + ".n3";
        System.out.println(this.tree + " gesetzt");
    }

}
