package com.sense.antlrastgenerator.node;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * A terminal node or leaf in a syntax tree
 */
class LeafNode extends Node {

    LeafNode(TerminalNode tree, CommonTokenStream tokenStream, Vocabulary vocabulary) {
        super(tree, tokenStream);
        final String symbolicNodeName = vocabulary.getSymbolicName(tree.getSymbol().getType());
        this.nodeType = symbolicNodeName == null ? null : "TerminalNode_" + symbolicNodeName;
        this.id = tree.getSymbol().getType() + 1000;
    }
}
