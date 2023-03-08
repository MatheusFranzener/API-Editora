package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.model.factory.PessoaFactory;
import br.senai.sc.editoralivros.model.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
@RequestMapping("/editoralivros/pessoa") // Criando a rota para as requisições
public class PessoaController {
    private PessoaService service;

//    public PessoaController(PessoaService service) {
//        this.service = service;
//    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() { // ResponseEntity é o retorno da requisição HTTP
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("cpf/{cpf}")
    public ResponseEntity<Object> findById(@PathVariable(value = "cpf") Long cpf) {
        Optional<Pessoa> pessoaOptional = service.findById(cpf);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a pessoa com esse cpf!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaOptional.get());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<Object> findByEmail(@PathVariable(value = "email") String email) {
        Optional<Pessoa> pessoaOptional = service.findByEmail(email);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrada nenhuma pessoa com este Email!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaOptional.get());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Object> update(@PathVariable(value = "cpf") Long cpf, @RequestBody @Valid PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaOptional = service.findById(cpf);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a pessoa com esse cpf!");
        }

        if (service.existsByEmail(pessoaDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O email já está em uso!");
        }

        Pessoa pessoa = pessoaOptional.get();
        BeanUtils.copyProperties(pessoaDTO, pessoa, "cpf");
        service.save(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa atualizada com sucesso!");
    }

//    @PostMapping("/{tipo}")
//    public ResponseEntity<Object> save(@PathVariable(value = "tipo") Integer tipo, @RequestBody @Valid PessoaDTO pessoaDTO) {
//
//        if (service.existsById(pessoaDTO.getCpf())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este cpf já está cadastrado!");
//        }
//
//        if (service.existsByEmail(pessoaDTO.getEmail())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este email já está cadastrado!");
//        }
//
//        Pessoa pessoa = new PessoaFactory().getPessoa(tipo);
//        BeanUtils.copyProperties(pessoaDTO, pessoa);
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        pessoa.setSenha(encoder.encode(pessoa.getSenha()));
//
//        return ResponseEntity.status(HttpStatus.OK).body(service.save(pessoa));
//    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PessoaDTO pessoaDTO) {

        if (service.existsById(pessoaDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este cpf já está cadastrado!");
        }

        if (service.existsByEmail(pessoaDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este email já está cadastrado!");
        }

        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        pessoa.setSenha(encoder.encode(pessoa.getSenha()));

        return ResponseEntity.status(HttpStatus.OK).body(service.save(pessoa));
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "cpf") Long cpf) {
        if (!service.existsById(cpf)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a pessoa com esse cpf!");
        }
        service.deleteById(cpf);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso!");
    }
}