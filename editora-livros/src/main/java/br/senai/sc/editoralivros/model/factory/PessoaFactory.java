package br.senai.sc.editoralivros.model.factory;

import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Diretor;
import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.model.entities.Revisor;

public class PessoaFactory {
    public Pessoa getPessoa(Integer tipoPessoa){

        switch (tipoPessoa){
            case 1:
                return new Autor();
            case 2:
                return new Revisor();
            case 3:
                return new Diretor();
        }

        return null;
    }
}
