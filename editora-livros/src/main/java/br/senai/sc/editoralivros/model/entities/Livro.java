package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_livros")
@AllArgsConstructor
@NoArgsConstructor()
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Livro {

    @Id
    @Column(length = 13, nullable = false, unique = true)
    private Long isbn;

    @Column(length = 50, nullable = false)
    private String titulo;

    @ManyToMany // tipo de relacionamento ( N : N )
    @JoinTable(
            name = "tb_livro_autor",
            joinColumns = @JoinColumn(name = "isbn_livro", nullable = false), // primeiro vai a tabela que possui a foreign key
            inverseJoinColumns = @JoinColumn(name = "cpf_autor", nullable = false) // depois a tabela que não possui a foreign key
    )
    private List<Autor> autores;

    @Column(nullable = false)
    private Integer qtdPag;

    @Column()
    private Integer qtdPaginasRevisadas;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "cpf_revisor")
    private Revisor revisor;

    @ManyToOne
    @JoinColumn(name = "cnpj_editora")
    private Editora editora;
}

