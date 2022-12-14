package br.senai.sc.editoralivros.model.service;

import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Livro;
import br.senai.sc.editoralivros.model.entities.Status;
import br.senai.sc.editoralivros.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LivroService {
    private LivroRepository repository;

    public Livro save(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> findById(Long isbn) {
        return repository.findById(isbn);
    }

    public List<Livro> findByStatus(Status status){
        return repository.findByStatus(status);
    }

    public List<Livro> findByAutor(Autor autor) {
        return repository.findByAutores(autor);
    }

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public Page<Livro> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void deleteById(Long isbn) {
        repository.deleteById(isbn);
    }

    public boolean existsById(Long isbn) {
        return repository.existsById(isbn);
    }

    public List<Livro> findByIsbnAndStatus(Long isbn, Status status) { return repository.findByIsbnAndStatus(isbn, status); }
}
