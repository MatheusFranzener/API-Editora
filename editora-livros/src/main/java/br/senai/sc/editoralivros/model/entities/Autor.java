package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor // Define um construtor com todos os atributos da classe
@Entity // Declara que a classe é uma entidade do banco de dados
public class Autor extends Pessoa {

}
