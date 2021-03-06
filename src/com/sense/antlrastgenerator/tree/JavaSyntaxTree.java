package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.java8.Java8Parser;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;

/**
 * Created by jakob on 23.11.17.
 */
public class JavaSyntaxTree extends AbstractSyntaxTree {

    Java8Parser parser;

    /** Sets up all the Antlr Objects (Lexer, TokenStream, Parser) and returns the input file as a abstract syntax tree representation*/
    public JavaSyntaxTree(final File file) {
        super(file);
        this.lexer =   new Java8Lexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(lexer);
        this.parser = new Java8Parser(commonTokenStream);
        this.antlrTree = parser.compilationUnit();
        this.nodes = createNodesList();
        this.dictionary = getDictionary();
    }
}
