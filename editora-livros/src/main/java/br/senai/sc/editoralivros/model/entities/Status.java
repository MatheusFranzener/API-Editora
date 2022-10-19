package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    AGUARDANDO_REVISAO("Aguardando revisão"),
    EM_REVISAO("Em Revisão"),
    APROVADO("Aprovado"),
    AGUARDANDO_EDICAO("Aguardando edição"),
    REPROVADO("Reprovado"),
    PUBLICADO("Publicado");

    String nome;
}
