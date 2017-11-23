package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.java8.Java8Parser;
import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;

import java.io.IOException;
import java.util.ArrayList;

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

    /**
     * A Node in an AST.
     */
    @AllArgsConstructor
    public static class Node {
        public String type;

        @Override
        public String toString() {
            return type;
        }
    }

    /**
     * Parses the input files to a AST, then collects the names (types) of all the nodes in a ArrayList
     * by performing of a depth-first search through the tree
     * @throws IOException
     */
    public static void main (String[] args) throws IOException {
        ParseTree javaTree = parseJava("examples/java/Demo.java");
        ParseTree pythonTree = parsePython("examples/python/Demo.py");

        final ArrayList<Node> javaNodes = collectNodes(javaTree);
        final ArrayList<Node> pythonNodes = collectNodes(pythonTree);
    }

    private static ArrayList<Node> collectNodes(ParseTree javaTree) {
        final ArrayList<Node> nodesCollection = new ArrayList<>();
        traverseTree(javaTree, nodesCollection);
        //print out nodes
        for(int i = 0; i < nodesCollection.size(); i++) {
            System.out.println(nodesCollection.get(i));
        }
        return nodesCollection;
    }

    public static void traverseTree(Tree tree, ArrayList<Node> nodes) {
        String nodeName = tree.getClass().getName().toString();

        nodeName = nodeName.substring(nodeName.lastIndexOf("$") + 1);
        nodes.add(new Node(nodeName));

        // traverse children
        int childCount = tree.getChildCount();
        if (childCount == 0) {
            // leaf node, we're done
        } else {
            for (int i = 0; i < childCount; i++) {
                Tree child = tree.getChild(i);
                traverseTree(child, nodes);
            }
        }
    }

    public static Java8Parser.CompilationUnitContext parseJava(String file) throws IOException {
        final CharStream charStreams = CharStreams.fromFileName(file);
        final Java8Lexer java8Lexer =   new Java8Lexer(charStreams);
        final CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
        final Java8Parser java8Parser = new Java8Parser(commonTokenStream);
        /* The compilationUnitContext is the AST object, compiliationUnit is the StartRuleName/Entry Point for the Grammar */

        return java8Parser.compilationUnit();
    }

    public static Python3Parser.Single_inputContext parsePython(String file) throws IOException {
        final CharStream charStreams = CharStreams.fromFileName(file);
        final Python3Lexer python3Lexer = new Python3Lexer(charStreams);
        final CommonTokenStream pythonTokenStream = new CommonTokenStream(python3Lexer);
        final Python3Parser python3Parser = new Python3Parser(pythonTokenStream);
        /* Single_Input is the StartRuleName */
        return python3Parser.single_input();
    }
}
