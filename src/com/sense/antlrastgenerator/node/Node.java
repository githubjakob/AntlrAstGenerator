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

    String type;
    Pair<Token, Token> tokens;
    int line; //line of first token
    int id;

    Node(@NotNull final ParseTree tree, @NotNull final CommonTokenStream tokenStream) {
        if (tokenStream == null || tree == null) {
            throw new NullPointerException("TokenStream or ParseTree is null, something went wrong with the ANTLR Parsing");
        }
        final Interval tokenInterval = tree.getSourceInterval(); //indexes of the token
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
        }}

    public int getId() {
        return this.id;
    }

    public int getLine() {
        return this.line;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return type;
    }
}
