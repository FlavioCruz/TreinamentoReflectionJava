package main;

import injection.annotation.ApplicationContext;
import injection.configuration.Executioner;
import injection.configuration.Parser;

import java.util.Scanner;

@ApplicationContext(Main.class)
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
        Parser parser = new Parser(url, Main.class);
        Object obj = parser.instantiateController();
        Executioner exec = new Executioner(obj, parser.getMethod(), parser.getParams());
        System.out.println(exec.execute());
    }
}