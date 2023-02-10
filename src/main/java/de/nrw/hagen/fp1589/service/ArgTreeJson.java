package de.nrw.hagen.fp1589.service;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;

import java.util.List;

public record ArgTreeJson(List<InformationNode> nodes) {
}
