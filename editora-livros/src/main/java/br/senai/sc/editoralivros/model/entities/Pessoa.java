package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity // Declara que a classe é uma entidade do banco de dados
@Table(name = "tb_pessoa") // Define o nome da tabela no banco de dados
@AllArgsConstructor @NoArgsConstructor() // Define um construtor com argumentos e um sem
@Getter @Setter // Define os métodos get e set para todos os atributos da classe
@ToString // Define o método toString
@EqualsAndHashCode // Define que o equals e o hashcode serão gerados com base nos atributos da classe
public class Pessoa {
    @Id
    @Column(length = 11, nullable = false, unique = true)
    private Long cpf;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String sobrenome;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(length = 25, nullable = false)
    private String senha;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Genero genero;
}
