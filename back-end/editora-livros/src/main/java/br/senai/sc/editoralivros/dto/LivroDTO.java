package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Autor;

import lombok.Getter;

import java.util.List;

@Getter
public class LivroDTO {

    private Long isbn;
    private String titulo;
    private Integer qtdPag;
    private List<Autor> autores;

}
