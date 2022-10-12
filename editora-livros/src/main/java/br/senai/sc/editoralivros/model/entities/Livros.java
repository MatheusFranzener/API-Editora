//package br.senai.sc.editoralivros.model.entities;
//
//import lombok.*;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "tb_livros")
//@AllArgsConstructor
//@Getter @Setter @ToString @EqualsAndHashCode
//public class Livros {
//    @Id
//    @Column(length = 13, nullable = false, unique = true)
//    private Long isbn;
//
//    @Column(length = 50, nullable = false)
//    private String titulo;
//
//    @Column(nullable = false)
//    private Autor autor;
//
//    @Column(nullable = false)
//    private Integer qtdPag;
//
//    @Column()
//    private Integer qtdPaginasRevisadas;
//
//    @Column(nullable = false)
//    private Status status;
//
//    @Column()
//    private Revisor revisor;
//
//    @Column
//    private Editora editora;
//}
//
