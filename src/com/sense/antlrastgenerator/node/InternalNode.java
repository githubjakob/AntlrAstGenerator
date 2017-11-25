package com.sense.antlrastgenerator.node;

/**
 * Created by jakob on 23.11.17.
 */

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

/**
 * A InternalNode in an AST.
 */
public class InternalNode extends Node {

    InternalNode(RuleContext tree, CommonTokenStream tokenStream) {
        super(tree, tokenStream);
        this.type = getTypeName(tree);
        this.id = tree.getRuleIndex();
    }

    private String getTypeName(RuleContext tree) {
        String nodeType = tree.getClass().getName().toString();
        nodeType = nodeType.substring(nodeType.lastIndexOf("$") + 1);
        int contextPos = nodeType.indexOf("Context");
        if (contextPos == -1) {
            return nodeType;
        } else {
            return nodeType.substring(0, contextPos);
        }
    }
}