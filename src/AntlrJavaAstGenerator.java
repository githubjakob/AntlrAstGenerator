import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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
public class AntlrJavaAstGenerator {

    public static void main (String[] args) throws IOException {
        CharStream charStreams = CharStreams.fromFileName("examples/java/Demo.java");
        Java8Lexer java8Lexer =   new Java8Lexer(charStreams);
        CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
        Java8Parser java8Parser = new Java8Parser(commonTokenStream);
        /* The compilationUnitContext is the AST object */
        Java8Parser.CompilationUnitContext compilationUnitContext = java8Parser.compilationUnit();
    }
}
