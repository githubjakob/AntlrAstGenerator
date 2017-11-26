package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.node.Node;
import com.sense.antlrastgenerator.node.NodeHelper;
import lombok.Getter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jakob on 23.11.17.
 */
public abstract class AbstractSyntaxTree {

    Lexer lexer;

    CharStream charStreams;

    CommonTokenStream commonTokenStream;

    /** The file with the code that was parsed */
    @Getter
    File file;

    /** This is the "original" tree output coming from ANTLR */
    @Getter
    ParseTree antlrTree;

    /** A flat representation of the tree with a list of all the nodes */
    List<Node> nodes;

    /** Each node has a id, which translates into a string type, this dict holds the matchings id -> name*/
    Map<Integer, String> dictionary;

    /** Number of syntax errors Antlr found during parsing */
    @Getter
    int syntaxErrors;

    public AbstractSyntaxTree(File file) {
        this.file = file;
        try {
            this.charStreams = CharStreams.fromFileName(this.file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Processing " + this.file);
    }

    public List<Integer> getIdVector() {
        return this.nodes.stream().map(node -> node.getId()).collect(Collectors.toList());
    }

    public List<Integer> getLineVector() {
        return this.nodes.stream().map(node -> node.getLineOfFirstToken()).collect(Collectors.toList());
    }

    public Map<Integer, String> getDictionary() {
        if (this.dictionary == null) {
            this.dictionary = createDictionary();
        }
        return this.dictionary;
    }

    private Map<Integer,String> createDictionary() {
        Map<Integer, String> dict = new TreeMap<>();
        for (int i = 0; i < nodes.size(); i++ ) {
            final Integer id = nodes.get(i).getId();
            final String name = nodes.get(i).getNodeType();
            dict.put(id, name);
        }
        return dict;
    }

    /**
     * Converts the ParseTree into a flat into a list of {@see Node} by performing a depth-first search through the tree
     * @return a list containing all the nodes of the tree
     */
    ArrayList<ParseTree> flattenAntlrTree() {
        final ArrayList<ParseTree> nodesCollection = new ArrayList<>();
        traverseTree(antlrTree, nodesCollection);
        return nodesCollection;
    }

    List<Node> createNodesList() {
        return flattenAntlrTree()
                .stream()
                .map(
                    node -> NodeHelper.newNode(node, this.commonTokenStream, this.lexer.getVocabulary()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Adds the root InternalNode of the tree to the collection, then (recursively) all the children of the node
     * @param tree
     * @param collection
     */
    private static void traverseTree(ParseTree tree, ArrayList<ParseTree> collection) {
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

    public String toString() {
        return toJSON().toString();
    }

    public JsonObject toJSON() {
        JsonArrayBuilder sequence = Json.createArrayBuilder();
        this.getIdVector().forEach(id -> sequence.add(id));

        JsonArrayBuilder lineNumbers = Json.createArrayBuilder();
        this.getLineVector().forEach(line -> lineNumbers.add(line));

        boolean compiled = this.getSyntaxErrors() == 0;

        return Json.createObjectBuilder()
                .add("name", this.file.getName())
                .add("compiled", compiled)
                .add("sequence", sequence)
                .add("lineNumbers", lineNumbers)
                .build();
    }
}
