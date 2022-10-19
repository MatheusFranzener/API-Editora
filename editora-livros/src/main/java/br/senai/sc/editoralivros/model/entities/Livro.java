package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne // tipo de relacionamento ( N : 1 )
    @JoinColumn(name = "cpf_autor", nullable = false) // tabela de relacionamento
    private Autor autor;

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

