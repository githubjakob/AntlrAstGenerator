package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.javascript.JavaScriptLexer;
import com.sense.antlrastgenerator.grammar.javascript.JavaScriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class JavaScriptSyntaxTree extends AbstractSyntaxTree {
    private JavaScriptLexer javaScriptLexer;

    private JavaScriptParser javaScriptParser;

    public JavaScriptSyntaxTree(String file) {
        this.file = file;
        try {
            this.charStreams = CharStreams.fromFileName(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.javaScriptLexer = new JavaScriptLexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(javaScriptLexer);
        this.javaScriptParser = new JavaScriptParser(commonTokenStream);
        /* Single_Input is the StartRuleName */
        this.antlrTree = javaScriptParser.program();
        this.nodes = getNodes();
        this.dictionary = getDictionary();
    }
}
