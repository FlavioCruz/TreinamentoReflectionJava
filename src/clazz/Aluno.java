package clazz;

public class Aluno {

    public void defaultMethod(){
        System.out.println("default method");
    }

    public void defaultMethod(String arg){
        System.out.println("default method with args: " + arg);
    }

    public void list(String matricula){
        System.out.println("list alunos " + matricula);
    }
}
