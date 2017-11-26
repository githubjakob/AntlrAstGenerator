package com.sense.antlrastgenerator.node;

import com.sun.istack.internal.NotNull;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by jakob on 25.11.17.
 */
public abstract class Node {

    String nodeType;

    int id;

    Pair<Token, Token> tokens;

    int lineOfFirstToken;

    Node(@NotNull final ParseTree tree, @NotNull final CommonTokenStream tokenStream) {
        if (tokenStream == null || tree == null) {
            throw new NullPointerException("TokenStream or ParseTree is null, something went wrong with the ANTLR Parsing");
        }
        final Interval tokenInterval = tree.getSourceInterval(); //indexes of the token
        final int beginTokenIndex = tokenInterval.a;
        final int endTokenIndex = tokenInterval.b;
        if (beginTokenIndex < 0 || endTokenIndex < 0) { // no token available
            this.tokens = new Pair<>(null, null);
            this.lineOfFirstToken = -1;
        } else {
            final Token beginToken = tokenStream.get(beginTokenIndex);
            final Token endToken = tokenStream.get(endTokenIndex);
            this.tokens = new Pair<>(beginToken, endToken);
            this.lineOfFirstToken = beginToken.getLine();
        }
    }

    public int getId() {
        return this.id;
    }

    public int getLineOfFirstToken() {
        return this.lineOfFirstToken;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public String toString() {
        return nodeType;
    }
}
