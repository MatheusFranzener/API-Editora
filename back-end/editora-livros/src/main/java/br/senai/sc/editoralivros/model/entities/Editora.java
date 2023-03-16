package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_editoras")
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Editora {

    @Id
    @Column(length = 14, nullable = false, unique = true)
    private Long cnpj;

    @Column(length = 70, nullable = false)
    private String nome;

}
