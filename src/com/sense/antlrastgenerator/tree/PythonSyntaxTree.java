package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Parser;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;

/**
 * Created by jakob on 23.11.17.
 */
public class PythonSyntaxTree extends AbstractSyntaxTree {

    private Python3Parser parser;

    public PythonSyntaxTree(File file) {
        super(file);
        this.lexer = new Python3Lexer(charStreams);
        this.commonTokenStream = new CommonTokenStream(lexer);
        this.parser = new Python3Parser(commonTokenStream);
        /* File_Input is the StartRuleName */
        this.antlrTree = parser.file_input();
        this.nodes = createNodesList();
        this.dictionary = getDictionary();
        this.syntaxErrors = parser.getNumberOfSyntaxErrors();
    }
}
