package br.senai.sc.editoralivros.model.service;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que a classe é um serviço
public class PessoaService {
    private PessoaRepository repository;

    public PessoaService(PessoaRepository pessoaDAO) {
        this.repository = pessoaDAO;
    }

    public boolean existsById(Long cpf) {
        return repository.existsById(cpf);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public List<Pessoa> findAll() {
        return repository.findAll();
    }

    public <S extends Pessoa> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<Pessoa> findById(Long cpf) {
        return repository.findById(cpf);
    }

    public void deleteById(Long cpf) {
        repository.deleteById(cpf);
    }

    public Optional<Pessoa> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
