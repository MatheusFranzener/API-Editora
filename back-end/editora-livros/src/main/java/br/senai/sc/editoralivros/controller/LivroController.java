package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Livro;
import br.senai.sc.editoralivros.model.entities.Status;
import br.senai.sc.editoralivros.model.service.LivroService;
import br.senai.sc.editoralivros.util.LivroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/editoralivros/livro")
public class LivroController {
    private LivroService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("livro") String livroJson, // agora o postman vai mandar uma string de json
                                       @RequestParam("arquivo") MultipartFile file) { // arquivo que será enviado

        // converter a string json para um objeto do tipo livro
        LivroUtil util = new LivroUtil();
        Livro livro = util.convertJsonToModel(livroJson);

        if (service.existsById(livro.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Livro já cadastrado!");
        }

        livro.setArquivo(file);
        livro.setStatus(Status.AGUARDANDO_REVISAO);

        return ResponseEntity.status(HttpStatus.OK).body(service.save(livro));
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<Object> findById(@PathVariable(value = "isbn") Long isbn) {
        Optional<Livro> livroOptional = service.findById(isbn);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O livro de ISBN: " + isbn + " não foi encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(livroOptional.get());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Livro>> findByStatus(@PathVariable(value = "status") Status status) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByStatus(status));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> findByAutor(@PathVariable(value = "autor") Autor autor) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByAutor(autor));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Livro>> findAllPage(@PageableDefault(
            page = 1, size = 18, sort = "isbn", direction = Sort.Direction.ASC
    ) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid LivroDTO livroDTO) {
        if (!service.existsById(livroDTO.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não foi possível encontrar o livro!");
        }

        Livro livroModel = service.findById(livroDTO.getIsbn()).get();
        BeanUtils.copyProperties(livroDTO, livroModel);
        return ResponseEntity.status(HttpStatus.OK).body(service.save(livroModel));
    }

    @Transactional
    @DeleteMapping("/isbn/{isbn}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "isbn") Long isbn) {
        if (!service.existsById(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar um livro com esse isbn!");
        }

        service.deleteById(isbn);
        return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso!");
    }
}
