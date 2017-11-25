package com.sense.antlrastgenerator.node;

import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by jakob on 25.11.17.
 */
public class LeafNode extends Node {

    LeafNode(TerminalNode tree, CommonTokenStream tokenStream) {
        super(tree, tokenStream);
        this.type = getTypeName(tree);
        this.id = getNodeId(tree);
    }

    private int getNodeId(TerminalNode tree) {
        final int type = tree.getSymbol().getType();
        return type * -1;
        //dictionary.put(id, this.type);
    }

    private String getTypeName(TerminalNode tree) {
        return "TerminalNode_" + Python3Lexer.VOCABULARY.getSymbolicName(tree.getSymbol().getType());
    }
}
