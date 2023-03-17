package de.nrw.hagen.fp1589.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.service.ArgTreeJson;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ArgTreeRestController {

    private String tree;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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
    public ArgTreeJson getconclusions(@RequestBody ArgTreeJson inodes) {
        ArgTree tree = ArgTreeReaderWriter.importTree(this.tree);
        ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(tree);

        //inodes.nodes().remove(0);
        List<InformationNode> testnodes = argTreeEvaluator.getConclusionForUser(inodes.nodes());
        System.out.println(testnodes.size());
        System.out.println(testnodes.get(0).getClaimText());

        return new ArgTreeJson(testnodes);
    }


    @GetMapping("/settree")
    public void setTree(@RequestParam String tree) {
        this.tree = tree + ".n3";
        System.out.println(this.tree + " gesetzt");
    }

}
