package com.sense.antlrastgenerator.node;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by jakob on 25.11.17.
 */
public abstract class NodeHelper {

        public static Node newNode(final ParseTree tree, final CommonTokenStream tokenStream, final Vocabulary vocabulary) {
            Node node = null;
            if (tree instanceof RuleContext) {
                node = new InternalNode((RuleContext) tree, tokenStream);

            } else if (tree instanceof TerminalNode) {
                node = new LeafNode((TerminalNode) tree, tokenStream, vocabulary);
            }

            if (node.getNodeType() == null) {
                //System.out.println("Skipping a node because it's token is not in the Lexer's vocabulary.");
                return null;
            }
            return node;
    }
}
