import java8.Java8Lexer;
import java8.Java8Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import python.Python3Lexer;
import python.Python3Parser;

import java.io.IOException;

/**
 * This class generates a Abstract Syntax Tree from a Demo.java file.
 *
 * It uses the Antlr Java8 Grammar:
 * https://github.com/antlr/grammars-v4/tree/master/java8
 *
 * Run
 * java -jar antlr.jar Java8.g4
 * to generate the Java8Listener, Java8Lexer and Java8Parser from the g4 file.
 *
 * Run
 * java -cp .:antlr.jar org.antlr.v4.gui.TestRig Java8 compilationUnit -gui examples/java/Demo.java
 * to show the AST as a graph in a GUI
 *
 */
public class AntlrAstGenerator {

    public static void main (String[] args) throws IOException {
        Java8Parser.CompilationUnitContext javaTree = parseJava("examples/java/Demo.java");
        Python3Parser.Single_inputContext pythonTree = parsePython("examples/python/Demo.py");
    }

    public static Java8Parser.CompilationUnitContext parseJava(String file) throws IOException {
        CharStream charStreams = CharStreams.fromFileName(file);
        Java8Lexer java8Lexer =   new Java8Lexer(charStreams);
        CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
        Java8Parser java8Parser = new Java8Parser(commonTokenStream);
        /* The compilationUnitContext is the AST object */
        return java8Parser.compilationUnit();
    }

    public static Python3Parser.Single_inputContext parsePython(String file) throws IOException {
        CharStream charStreams = CharStreams.fromFileName(file);
        Python3Lexer python3Lexer = new Python3Lexer(charStreams);
        CommonTokenStream pythonTokenStream = new CommonTokenStream(python3Lexer);
        Python3Parser python3Parser = new Python3Parser(pythonTokenStream);
        return python3Parser.single_input();
    }
}
