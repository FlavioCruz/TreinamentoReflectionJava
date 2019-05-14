package main.controller;

import injection.annotation.Controller;
import injection.annotation.Inject;
import main.service.AlunoService;

@Controller
public class AlunoController {

    @Inject
    AlunoService alunoService;
}
