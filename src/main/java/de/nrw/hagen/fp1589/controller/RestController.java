package de.nrw.hagen.fp1589.controller;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.service.ArgTreeJson;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/hello")
    public ArgTreeJson hello() {
        ArgTree tree = ArgTreeReaderWriter.importTree("ArgSchlafstoerungen_arg.n3");
        return new ArgTreeJson(tree);

    }

}
