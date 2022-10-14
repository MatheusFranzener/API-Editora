package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.model.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editoralivros/livro")
public class LivroController {
    private LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }


}
