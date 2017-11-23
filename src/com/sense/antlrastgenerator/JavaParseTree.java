package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.java8.Java8Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by jakob on 23.11.17.
 */
public class JavaParseTree extends AntlrParseTree {

    private Java8Lexer java8Lexer;

    private Java8Parser java8Parser;

    /** Sets up all the Antlr Objects (Lexer, TokenStream, Parser) and returns the input file as a abstract syntax tree representation*/
    JavaParseTree(final String file) {
        this.file = file;
        try {
            charStreams = CharStreams.fromFileName(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        java8Lexer =   new Java8Lexer(charStreams);
        commonTokenStream = new CommonTokenStream(java8Lexer);
        java8Parser = new Java8Parser(commonTokenStream);
        parseTree = java8Parser.compilationUnit();
        System.out.println("constructor");
    }
}
