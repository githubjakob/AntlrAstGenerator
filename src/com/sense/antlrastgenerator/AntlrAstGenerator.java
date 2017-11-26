package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.tree.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.File;
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

    public static void main (String[] args) throws Exception {

        List<PythonSyntaxTree> pythonTrees = generateFromPath("examples/python/", PythonSyntaxTree.class);
        System.out.println(AntlrAstGenerator.toJson(pythonTrees));
        System.out.println(AntlrAstGenerator.composeDict(pythonTrees));

        //List<JavaSyntaxTree> javaTrees = generateFromPath("examples/java/", JavaSyntaxTree.class);
        //List<JavaScriptSyntaxTree> javaScriptSyntaxTrees = generateFromPath("examples/javascript/", JavaScriptSyntaxTree.class);
        //List<CSyntaxTree> cSyntaxTrees = generateFromPath("examples/c/", CSyntaxTree.class);

        System.out.println("Done");
    }

    private static <T extends AbstractSyntaxTree> List<T> generateFromPath (String pathname, Class<T> returnType) throws Exception {
        List<T> result = new ArrayList<>();
        final File directory = new File(pathname);
        final List<File> files = Arrays.asList(directory.listFiles());

        for (File input : files) {
            // call constructor of returnType, e.g. JavaScriptSyntaxTree, JavaSyntaxTree
            T parsedTree = returnType.getConstructor(File.class).newInstance(input);
            validateOutput(parsedTree);
            result.add(parsedTree);
        }
        return result;
    }

    private static String toJson(List<PythonSyntaxTree> trees) {
        JsonArrayBuilder treesArray = Json.createArrayBuilder();
        trees.forEach(tree -> treesArray.add(tree.toJSON()));
        return treesArray.build().toString();
    }

    private static Map<Integer, String> composeDict(List<PythonSyntaxTree> trees) {
        Map<Integer, String> dict = new HashMap<>();
        trees.forEach(tree -> dict.putAll(tree.getDictionary()));
        return dict;
    }

    private static boolean validateOutput(AbstractSyntaxTree tree) {
        int errors = 0;
        /** Lines Vector must be strictely ordered */
        final List<Integer> lines = tree.getLineVector();
        final List<Integer> sorted = lines.stream().sorted().collect(Collectors.toList());
        if (!lines.equals(sorted)) {
            System.out.println("File " + tree.getFile().getName() + " Warning: lineVector has no strict ordering");
            errors++;
        }

        /** LineVector and idVector must hate same length */
        final List<Integer> ids = tree.getIdVector();
        if (ids.size() != lines.size() ) {
            System.out.println("File " + tree.getFile().getName() + " Warning: LineVector and idVector must hate same length");
            errors++;
        }

        /** Show a warning in case a Tree had syntax errors **/
        if (tree.getSyntaxErrors() > 0) {
            System.out.println("File " + tree.getFile().getName() + " Warning: There was a syntax error during parsing");
            errors++;
        }

        if (errors == 0) {
            return true;
        }
        return false;
    }
}
