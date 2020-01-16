package main.model;

import injection.annotation.Entity;

@Entity
public class Aluno {

    public void defaultMethod(){
        System.out.println("default method");
    }

    public void defaultMethod(String arg){
        System.out.println("default method with args: " + arg);
    }

    public String list(Integer matricula){
        return "list method with parameter: " + matricula;
    }
}
