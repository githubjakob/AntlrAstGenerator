# Antlr Java Ast Generator
https://github.com/githubjakob/AntlrAstGenerator

This projects generates an Abstract Syntax Tree (AST) from Java sourcecode.

## Goal
Generate an AST from Sourcecode for various input languages using Antlr4.

### Target languages
* Java
* JavaScript
* C++
* C
* C#
* Python

## Dependencies
* Antrl 4.7 from Maven org.antlr:antlr4:4.7 https://github.com/antlr/antlr4
* Java8 Grammar https://github.com/antlr/grammars-v4/tree/master/java8

## Generate AST using Antrl TestRig and Gui

```
java -jar antlr.jar Java8.g4 

javac -cp ./antlr.jar *.java

java -cp .:antlr.jar org.antlr.v4.gui.TestRig Java8 compilationUnit -gui Test.java
```

## Status other languages

Other languages currently work with Antlr in the command line:

###Java8


java -jar antlr.jar Java8.g4 

javac -cp ./antlr.jar *.java

StartRuleName: compilationUnit

https://github.com/antlr/grammars-v4/tree/master/java8


### Python3

java -jar antlr.jar Python3.g4

javac -cp ./antlr.jar *.java

https://github.com/antlr/grammars-v4/blob/master/python3/Python3.g4

startRuleName: single_input

java -cp .:../antlr/antlr.jar org.antlr.v4.gui.TestRig Python3 single_input -gui Test.py


### JavaScript


https://github.com/antlr/grammars-v4/tree/master/javascript

Download both Lexer and Parser g4

antlr4 JavaScriptParser.g4 
antlr4 JavaScriptLexer.g4 

download also the /java/JavaScriptBaseLexer.java and JavaScriptBaseParser.java

compile everything
javac *.java


java -cp .:../../antlr/antlr.jar org.antlr.v4.gui.TestRig JavaScript program -gui Test.js


### CPP

java -cp .:../../antlr/antlr.jar org.antlr.v4.gui.TestRig CPP14 translationunit -gui Test.cc


### C

java -cp .:../../antlr/antlr.jar org.antlr.v4.gui.TestRig C compilationUnit -gui Test.c


### CSharp


java -cp .:../../antlr/antlr.jar org.antlr.v4.gui.TestRig CSharp compilation_unit -gui Test.cs
