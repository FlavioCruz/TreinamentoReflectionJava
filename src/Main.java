import configuration.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner tc = new Scanner(System.in);

        String url = tc.next();

        Parser parser = new Parser(url);


        Object obj = parser.execute();

        System.out.println();
    }
}
