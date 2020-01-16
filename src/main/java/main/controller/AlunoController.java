package main.controller;

import injection.annotation.Controller;
import injection.annotation.Inject;
import main.service.AlunoService;

@Controller
public class AlunoController {

    @Inject
    AlunoService alunoService;

    public void defaultMethod(){
        alunoService.defaultMethod();
    }

    public void defaultMethod(String arg){
        alunoService.defaultMethod(arg);
    }

    public String list(Integer matricula){
        return alunoService.list(matricula);
    }
}
