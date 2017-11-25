package com.sense.antlrastgenerator;

import com.sense.antlrastgenerator.tree.JavaSyntaxTree;
import com.sense.antlrastgenerator.tree.PythonSyntaxTree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<JavaSyntaxTree> javaTrees = generateFromPath("examples/java/", JavaSyntaxTree.class);

        System.out.println("Done");
    }

    private static <T> List<T> generateFromPath (String pathname, Class<T> returnType) throws Exception {
        List<T> result = new ArrayList<>();
        final File directory = new File(pathname);
        final List<File> files = Arrays.asList(directory.listFiles());

        for (File input : files) {
            result.add(returnType.getConstructor(String.class).newInstance(input.toString()));
        }

        return result;
    }
}
