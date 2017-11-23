package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Parser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.*;

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

    private static CommonTokenStream tokenStream = null;

    private static Map<Integer, String> dictionary = new TreeMap<>();



    /**
     * Parses the input files to a AST, then collects the names (types) of all the nodes in a ArrayList
     * by performing of a depth-first search through the tree
     * @throws IOException
     */
    public static void main (String[] args) throws IOException {

        /*JavaParseTree javaParseTree = new JavaParseTree("examples/java/Demo.java");
        List<Node> nodes = javaParseTree.getSimpliefiedNodes();
        List<Integer> idVector = javaParseTree.getIdVector();
        List<Integer> lineVector = javaParseTree.getLineVector();
        Map<Integer, String> dict = javaParseTree.getDictionary();*/


        PythonParseTree pythonParseTree = new PythonParseTree("examples/python/Demo.py");

        List<Node> pyNodes = pythonParseTree.getSimpliefiedNodes();
        List<Integer> pyIdVector = pythonParseTree.getIdVector();
        List<Integer> pyLineVector = pythonParseTree.getLineVector();
        Map<Integer, String> pyDict = pythonParseTree.getDictionary();



        //ParseTree javaTree = parseJava("examples/java/Demo.java");
        //ParseTree pythonTree = parsePython("examples/python/Demo.py");

        //final ArrayList<ParseTree> javaNodes = collectNodes(javaTree); // full ParseTree Objects
        //final ArrayList<Node> pythonNodes = collectNodes(pythonTree);


        //List<Node> nodeVector = javaNodes.stream().map(node -> new Node(node)).collect(Collectors.toList());
        //List<Integer> idVector = nodeVector.stream().map(node -> node.getId()).collect(Collectors.toList());
        //List<Integer> lineVector = nodeVector.stream().map(node -> node.getLine()).collect(Collectors.toList());

        System.out.println("Done");
    }
}
