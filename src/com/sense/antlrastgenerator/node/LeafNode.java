package com.sense.antlrastgenerator.node;

import com.sense.antlrastgenerator.grammar.javascript.JavaScriptLexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * A terminal node or leaf in a syntax tree
 */
class LeafNode extends Node {

    LeafNode(TerminalNode tree, CommonTokenStream tokenStream) {
        super(tree, tokenStream);
        // TODO fix this for other grammars
        this.type = "TerminalNode_" + JavaScriptLexer.VOCABULARY.getSymbolicName(tree.getSymbol().getType());
        this.id = tree.getSymbol().getType() * -1;
    }
}
