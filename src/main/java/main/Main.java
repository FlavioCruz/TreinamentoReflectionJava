package main;

import injection.annotation.ApplicationContext;
import injection.configuration.Executioner;
import injection.configuration.Parser;

import java.util.Scanner;

@ApplicationContext
public class Main {
    public static void main(String[] args){
        Scanner tc = new Scanner(System.in);

        /**
         * URL example
         * controller
         * controller?param=value
         * controller/method?param=value
         * controller/method?param1=value&param2=value
         *
         * code examples
         * aluno
         * aluno?matricula=123456789
         * aluno/list?matricula=123456789
         * @param url
         */
        String url = tc.next();
        Parser parser = new Parser(url);
        Executioner exec = new Executioner(parser);
        System.out.println(exec.execute());
    }
}
