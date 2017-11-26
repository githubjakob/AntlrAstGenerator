package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.c.CLexer;
import com.sense.antlrastgenerator.grammar.c.CParser;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;

/**
 * Created by jakob on 25.11.17.
 */
public class CSyntaxTree extends AbstractSyntaxTree {

    CParser parser;

    public CSyntaxTree(final File file) {
        super(file);
        this.lexer =   new CLexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(lexer);
        this.parser = new CParser(commonTokenStream);
        this.antlrTree = parser.compilationUnit();
        this.nodes = createNodesList();
        this.dictionary = getDictionary();
    }
}
