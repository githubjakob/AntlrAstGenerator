package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.java8.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by jakob on 23.11.17.
 */
public class JavaSyntaxTree extends AbstractSyntaxTree {

    private Java8Lexer java8Lexer;

    private Java8Parser java8Parser;

    /** Sets up all the Antlr Objects (Lexer, TokenStream, Parser) and returns the input file as a abstract syntax tree representation*/
    public JavaSyntaxTree(final String file) {
        this.file = file;
        try {
            charStreams = CharStreams.fromFileName(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        java8Lexer =   new Java8Lexer(charStreams);
        commonTokenStream = new CommonTokenStream(java8Lexer);
        java8Parser = new Java8Parser(commonTokenStream);
        antlrTree = java8Parser.compilationUnit();
    }
}
