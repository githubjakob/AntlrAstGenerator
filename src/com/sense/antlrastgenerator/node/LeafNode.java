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
        this.nodeType = "TerminalNode_" + vocabulary.getSymbolicName(tree.getSymbol().getType());
        this.id = tree.getSymbol().getType() * -1;
    }
}
