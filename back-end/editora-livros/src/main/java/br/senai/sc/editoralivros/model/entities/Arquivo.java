package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_arquivos")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor // construtor personalizado
@Getter
@Setter
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private String tipo;

    @NonNull
    @Lob            // identifica que é do tipo binário
    private byte[] dados;


}
