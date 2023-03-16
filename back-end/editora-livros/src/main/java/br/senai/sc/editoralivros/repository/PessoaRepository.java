package br.senai.sc.editoralivros.repository;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que a classe é um repositório
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByEmail(String email);

    boolean existsByEmail(String email);
}
