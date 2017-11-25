package com.sense.antlrastgenerator.node;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

/**
 * A InternalNode in an AST.
 */
class InternalNode extends Node {

    InternalNode(RuleContext tree, CommonTokenStream tokenStream) {
        super(tree, tokenStream);
        this.nodeType = createTypeName(tree);
        this.id = tree.getRuleIndex();
    }

    private String createTypeName(RuleContext tree) {
        String nodeType = tree.getClass().getName();
        nodeType = nodeType.substring(nodeType.lastIndexOf("$") + 1);
        int contextPos = nodeType.indexOf("Context");
        return nodeType.substring(0, contextPos);
    }
}