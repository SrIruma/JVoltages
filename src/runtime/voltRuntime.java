package runtime;

import ast.Statement;
import inter.Interpreter;
import lex.ModuleProcessor;
import lex.VoltLexer;
import parser.Parser;
import sem.Resolver;
import style.VoltCheckStyle;
import token.Token;
import token.TokenType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class voltRuntime {
    private static boolean hadError = false;
    private static boolean hadRuntimeError = false;
    private static final Interpreter interpreter = new Interpreter();
    public static void runVoltFile(String path) {
        ModuleProcessor preProcessor = new ModuleProcessor();
        String source = preProcessor.process(path);
        runVoltCode(source);
        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }

    public static void runVoltTerminal() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        System.out.println("Welcome to JVoltage Terminal v1.2!");
        while(true) {
            System.out.print("|> ");
            runVoltCode(reader.readLine());
            hadError = false;
        }
    }

    public static void runVoltCode(String source) {
        VoltLexer tankLexer = new VoltLexer(source);
        List<Token> tokens = tankLexer.scanTokens();
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        // Stop if there was a semantic error.
        if (hadError) return;

        //Start Volt Interpreter
        interpreter.interpret(statements);
    }

    public static void checkFileCodeStyle(String path) {
        ModuleProcessor preProcessor = new ModuleProcessor();
        String source = preProcessor.process(path);

        VoltLexer voltLexer = new VoltLexer(source);
        List<Token> tokens = voltLexer.scanTokens();
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();

        VoltCheckStyle checkStyle = new VoltCheckStyle();
        checkStyle.checkCodeStyle(statements);
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void error(String message) {
        report(message);
    }

    public static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    private static void report(String message) {
        System.err.println(message);
        hadError = true;
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() +" \n[line " + error.getToken().line + "]");
        hadRuntimeError = true;
    }
}