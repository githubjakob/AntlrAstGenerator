package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.node.Node;
import com.sense.antlrastgenerator.tree.JavaSyntaxTree;
import com.sense.antlrastgenerator.tree.PythonSyntaxTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class generates a Abstract Syntax Tree from a Demo file.
 *
 * It uses the Antlr Grammar:
 * https://github.com/antlr/grammars-v4/tree/master/java8
 * https://github.com/antlr/grammars-v4/blob/master/python3
 *
 * Run
 * java -jar antlr.jar -no-listener -no-visitor -package com.sense.antlrastgenerator.grammar.java8 Java8.g4
 * to generate the Java8Listener, Java8Lexer and Java8Parser from the g4 file.
 *
 * Run
 * java -cp .:antlr.jar org.antlr.v4.gui.TestRig Java8 compilationUnit -gui examples/java/Demo.java
 * to show the AST as a graph in a GUI
 *
 */
public class AntlrAstGenerator {

    public static void main (String[] args) throws IOException {

        JavaSyntaxTree javaSyntaxTree = new JavaSyntaxTree("examples/java/Demo.java");
        List<Node> nodes = javaSyntaxTree.getNodes();
        List<Integer> idVector = javaSyntaxTree.getIdVector();
        List<Integer> lineVector = javaSyntaxTree.getLineVector();
        Map<Integer, String> dict = javaSyntaxTree.getDictionary();

        PythonSyntaxTree pythonSyntaxTree = new PythonSyntaxTree("examples/python/Demo.py");
        List<Node> pyNodes = pythonSyntaxTree.getNodes();
        List<Integer> pyIdVector = pythonSyntaxTree.getIdVector();
        List<Integer> pyLineVector = pythonSyntaxTree.getLineVector();
        Map<Integer, String> pyDict = pythonSyntaxTree.getDictionary();

        System.out.println("Done");
    }
}
