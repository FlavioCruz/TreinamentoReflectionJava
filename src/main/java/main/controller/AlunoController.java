package main.controller;

import injection.annotation.Controller;
import injection.annotation.Inject;
import main.service.AlunoServiceImpl;

@Controller
public class AlunoController {

    @Inject
    AlunoServiceImpl alunoService;

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
