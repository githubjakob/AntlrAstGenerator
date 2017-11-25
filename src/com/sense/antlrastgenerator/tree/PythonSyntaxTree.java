package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by jakob on 23.11.17.
 */
public class PythonSyntaxTree extends AbstractSyntaxTree {

    private Python3Lexer python3Lexer;

    private Python3Parser python3Parser;

    public PythonSyntaxTree(String file) {
        this.file = file;
        try {
            this.charStreams = CharStreams.fromFileName(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.python3Lexer = new Python3Lexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(python3Lexer);
        this.python3Parser = new Python3Parser(commonTokenStream);
        /* Single_Input is the StartRuleName */
        this.antlrTree = python3Parser.single_input();
        this.nodes = getNodes();
        this.dictionary = getDictionary();
    }
}
