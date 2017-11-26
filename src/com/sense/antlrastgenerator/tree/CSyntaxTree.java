package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.c.CLexer;
import com.sense.antlrastgenerator.grammar.c.CParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by jakob on 25.11.17.
 */
public class CSyntaxTree extends AbstractSyntaxTree {

    CParser parser;

    public CSyntaxTree(final String file) {
        this.file = file;
        try {
            this.charStreams = CharStreams.fromFileName(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.lexer =   new CLexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(lexer);
        this.parser = new CParser(commonTokenStream);
        this.antlrTree = parser.compilationUnit();
        this.nodes = createNodesList();
        this.dictionary = getDictionary();
    }
}
