package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.javascript.JavaScriptLexer;
import com.sense.antlrastgenerator.grammar.javascript.JavaScriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

public class JavaScriptSyntaxTree extends AbstractSyntaxTree {

    JavaScriptParser parser;

    public JavaScriptSyntaxTree(File file) {
        super(file);
        try {
            this.charStreams = CharStreams.fromFileName(this.file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.lexer = new JavaScriptLexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(lexer);
        this.parser = new JavaScriptParser(commonTokenStream);
        /* Program is the StartRuleName */
        this.antlrTree = parser.program();
        this.nodes = createNodesList();
        this.dictionary = getDictionary();
    }
}
