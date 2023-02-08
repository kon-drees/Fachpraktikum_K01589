package de.nrw.hagen.fp1589.controller;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.service.ArgTreeJson;
import de.nrw.hagen.fp1589.service.MemoryService;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArgTreeRestController {

    private String tree;

    @GetMapping("/gettree")
    public ArgTreeJson gettree() {
        ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        //ArgTree tree = ArgTreeReaderWriter.importTree(this.tree);
        return new ArgTreeJson(tree);
    }


    @GetMapping("/settree")
    public void setTree(@RequestParam String tree) {
        this.tree = tree + ".n3";
        System.out.println(this.tree + " gesetzt");
    }

}
