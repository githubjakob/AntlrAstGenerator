package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.grammar.java8.Java8Lexer;
import com.sense.antlrastgenerator.grammar.java8.Java8Parser;
import com.sense.antlrastgenerator.grammar.python3.Python3Lexer;
import com.sense.antlrastgenerator.grammar.python3.Python3Parser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
     * A Node in an AST.
     */
    @AllArgsConstructor
    public static class Node {
        public String type;
        public Pair<Token, Token> tokens;
        @Getter public int line; //line of first token
        @Getter public int id;

        Node(ParseTree tree) {
            this.type = getTypeName(tree);
            final Interval tokenInterval = tree.getSourceInterval(); //indexes of the token
            if (tokenStream == null) {
                throw new NullPointerException("Token Stream is null, something went wrong with the ANTLR Parsing");
            }
            final Token beginToken = tokenStream.get(tokenInterval.a);
            final Token endToken = tokenStream.get(tokenInterval.b);
            this.tokens = new Pair<>(beginToken, endToken);
            this.line = beginToken.getLine();
            this.id = getNodeId(tree);
        }

        private int getNodeId(ParseTree tree) {
            int id;
            if (tree instanceof RuleContext) {
                RuleContext ruleContext = (RuleContext) tree;
                id = ruleContext.getRuleIndex();
                dictionary.put(id, this.type);
            } else if (tree instanceof TerminalNode){
                final TerminalNode terminalNode = (TerminalNode) tree;
                final int type = terminalNode.getSymbol().getType();
                id = type * -1;
                dictionary.put(id, this.type);
            } else {
                id = -2;
            }
            return id;
        }

        private String getTypeName(ParseTree tree) {
            if (tree instanceof RuleContext) {
                String nodeType = tree.getClass().getName().toString();
                nodeType = nodeType.substring(nodeType.lastIndexOf("$") + 1);
                int contextPos = nodeType.indexOf("Context");
                if (contextPos == -1) {
                    return nodeType;
                } else {
                    return nodeType.substring(0, contextPos);
                }
            } else if (tree instanceof TerminalNode) {
                final TerminalNode terminalNode = (TerminalNode) tree;
                return "TerminalNode_" + Java8Lexer.VOCABULARY.getSymbolicName(((TerminalNode) tree).getSymbol().getType());
            } else {
                return "";
            }
        }

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

        final ArrayList<ParseTree> javaNodes = collectNodes(javaTree); // full ParseTree Objects
        //final ArrayList<Node> pythonNodes = collectNodes(pythonTree);


        List<Node> nodeVector = javaNodes.stream().map(node -> new Node(node)).collect(Collectors.toList());
        List<Integer> idVector = nodeVector.stream().map(node -> node.getId()).collect(Collectors.toList());
        List<Integer> lineVector = nodeVector.stream().map(node -> node.getLine()).collect(Collectors.toList());

        System.out.println("Done");
    }

    /**
     * Converts the ParseTree into a flat into a list of {@see Node} by performing a depth-first search through the tree
     * @param javaTree the Antlr ParseTree that should be converted to the list
     * @return a list containing all the nodes of the tree
     */
    private static ArrayList<ParseTree> collectNodes(ParseTree javaTree) {
        final ArrayList<ParseTree> nodesCollection = new ArrayList<>();
        traverseTree(javaTree, nodesCollection);
        return nodesCollection;
    }

    /**
     * Adds the root Node of the tree to the collection, then (recursively) all the children of the node
     * @param tree
     * @param collection
     */
    public static void traverseTree(ParseTree tree, ArrayList<ParseTree> collection) {
        int childCount = tree.getChildCount();

        if (childCount != 1) {
            collection.add(tree); // only collect node if it's not a leaf (more than 0), and has more than one children
        }

        // traverse children
        if (childCount == 0) {
            // leaf node, we're done
        } else {
            for (int i = 0; i < childCount; i++) {
                ParseTree child = tree.getChild(i);
                traverseTree(child, collection);
            }
        }
    }

    /**
     * Sets up all the Antlr Objects (Lexer, TokenStream, Parser) and returns the input file as a abstract syntax tree representation
     * @param file path to a valid .java file containing the java code that should be parsed
     * @return the tree representation of the code (in Java with the CompilationUnit as the root node)
     * @throws IOException
     */
    public static ParseTree parseJava(String file) throws IOException {
        final CharStream charStreams = CharStreams.fromFileName(file);
        final Java8Lexer java8Lexer =   new Java8Lexer(charStreams);
        final CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
        tokenStream = commonTokenStream; // set as global var, idk if this is a good idea lol
        final Java8Parser java8Parser = new Java8Parser(commonTokenStream);
        /* The compiliationUnit is the top node of the tree / the startRule of the Grammar*/
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
