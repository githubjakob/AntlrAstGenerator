package com.sense.antlrastgenerator.tree;

import com.sense.antlrastgenerator.node.Node;
import com.sense.antlrastgenerator.node.NodeHelper;
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
abstract class AbstractSyntaxTree {

    CharStream charStreams;

    String file;

    /** This is the "original" tree output coming from ANTLR */
    ParseTree antlrTree;

    List<Node> nodes;

    Map<Integer, String> dictionary;

    CommonTokenStream commonTokenStream;

    public List<Node> getNodes() {
        if (this.nodes == null) {
            this.nodes = createNodesList();
        }
        return this.nodes;
    }

    public List<Integer> getIdVector() {
        return getNodes().stream().map(node -> node.getId()).collect(Collectors.toList());
    }

    public List<Integer> getLineVector() {
        return getNodes().stream().map(node -> node.getLine()).collect(Collectors.toList());
    }

    public Map<Integer, String> getDictionary() {
        if (this.dictionary == null) {
            this.dictionary = createDictionary();
        }
        return this.dictionary;
    }

    private Map<Integer,String> createDictionary() {
        final List<Node> nodes = getNodes();
        Map<Integer, String> dict = new TreeMap<>();
        for (int i = 0; i < nodes.size(); i++ ) {
            final Integer id = nodes.get(i).getId();
            final String name = nodes.get(i).getType();
            dict.put(id, name);
        }
        return dict;
    }

    private List<Node> createNodesList() {
        return flattenAntlrTree().stream().map(node -> NodeHelper.newNode(node, this.commonTokenStream)).collect(Collectors.toList());
    }

    /**
     * Converts the ParseTree into a flat into a list of {@see Node} by performing a depth-first search through the tree
     * @return a list containing all the nodes of the tree
     */
    private ArrayList<ParseTree> flattenAntlrTree() {
        final ArrayList<ParseTree> nodesCollection = new ArrayList<>();
        traverseTree(antlrTree, nodesCollection);
        return nodesCollection;
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
}
