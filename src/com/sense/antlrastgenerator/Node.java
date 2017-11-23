package com.sense.antlrastgenerator;

/**
 * Created by jakob on 23.11.17.
 */

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * A Node in an AST.
 */
@AllArgsConstructor
public class Node {
    @Getter public String type;
    public Pair<Token, Token> tokens;
    @Getter
    public int line; //line of first token
    @Getter public int id;

    Node(ParseTree tree, CommonTokenStream tokenStream) {
        this.type = getTypeName(tree);
        final Interval tokenInterval = tree.getSourceInterval(); //indexes of the token
        if (tokenStream == null) {
            throw new NullPointerException("Token Stream is null, something went wrong with the ANTLR Parsing");
        }
        final int beginTokenIndex = tokenInterval.a;
        final int endTokenIndex = tokenInterval.b;
        if (beginTokenIndex > 0 && endTokenIndex > 0) {
            final Token beginToken = tokenStream.get(beginTokenIndex);
            final Token endToken = tokenStream.get(endTokenIndex);
            this.tokens = new Pair<>(beginToken, endToken);
            this.line = beginToken.getLine();
        } else {
            this.tokens = new Pair<>(null, null); // TODO fix this hack
            this.line = -1;
        }

        this.id = getNodeId(tree);
    }

    private int getNodeId(ParseTree tree) {
        int id;
        if (tree instanceof RuleContext) {
            RuleContext ruleContext = (RuleContext) tree;
            id = ruleContext.getRuleIndex();
            //dictionary.put(id, this.type);
        } else if (tree instanceof TerminalNode){
            final TerminalNode terminalNode = (TerminalNode) tree;
            final int type = terminalNode.getSymbol().getType();
            id = type * -1;
            //dictionary.put(id, this.type);
        } else {
            id = -2;
        }
        return id;
    }

    private String getTypeName(ParseTree tree) {
        if (tree instanceof RuleContext) {
            String nodeType = tree.getClass().getName().toString();
            nodeType = nodeType.substring(nodeType.lastIndexOf("$") + 1);
            int contextPos = nodeType.indexOf("Context");
            if (contextPos == -1) {
                return nodeType;
            } else {
                return nodeType.substring(0, contextPos);
            }
        } else if (tree instanceof TerminalNode) {
            final TerminalNode terminalNode = (TerminalNode) tree;
            return "TerminalNode_" + Python3Lexer.VOCABULARY.getSymbolicName(((TerminalNode) tree).getSymbol().getType());
            // TODO fix for java and python parser
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return type;
    }
}