package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Genero;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PessoaDTO {

    private Long cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Genero genero;

}
