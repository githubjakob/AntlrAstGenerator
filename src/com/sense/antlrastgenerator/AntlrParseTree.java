package com.sense.antlrastgenerator;

import lombok.Getter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by jakob on 23.11.17.
 */
abstract class AntlrParseTree {

    public CharStream charStreams;


    public String file;

    public ParseTree parseTree;

    public CommonTokenStream commonTokenStream;

    /**
     * Converts the ParseTree into a flat into a list of {@see Node} by performing a depth-first search through the tree
     * @return a list containing all the nodes of the tree
     */
    public ArrayList<ParseTree> getFullNodes() {
        final ArrayList<ParseTree> nodesCollection = new ArrayList<>();
        traverseTree(parseTree, nodesCollection);
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

    public List<Node> getSimpliefiedNodes() {
        return getFullNodes().stream().map(node -> new Node(node, this.commonTokenStream)).collect(Collectors.toList());
    }

    public List<Integer> getIdVector() {
        return getSimpliefiedNodes().stream().map(node -> node.getId()).collect(Collectors.toList());
    }

    public List<Integer> getLineVector() {
        return getSimpliefiedNodes().stream().map(node -> node.getLine()).collect(Collectors.toList());
    }

    public Map<Integer, String> getDictionary() {
        final List<Node> nodes = getSimpliefiedNodes();
        Map<Integer, String> dict = new TreeMap<>();
        for (int i = 0; i < nodes.size(); i++ ) {
            final Integer id = nodes.get(i).getId();
            final String name = nodes.get(i).getType();
            dict.put(id, name);
        }
        return dict;
    }

}
