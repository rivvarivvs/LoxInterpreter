package lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class LoxMain {

    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if(args.length>1) {
            System.out.println("Usage jlox [script]");
            System.exit(64);
        } else if(args.length==1) {
            // if you give the cli a path to a file,
            // it reads it and runs
            runFile(args[0]);
        }else {
            // the cli can also run prompts,
            // if no arguments are passed
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
      }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
    
        for (;;) { 
          System.out.print("> ");
          String line = reader.readLine();
          if (line == null) break;
          run(line);
        }
      }
    

    private static void run(String source) {
        ScannerLox scanner = new ScannerLox(source);
        List<Token> tokens = scanner.scanTokens();

        // for now, just print the tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }


}
