package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import br.senai.sc.editoralivros.security.users.UserJpa;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

public class TokenUtils {

    private final String senhaForte = "05a9e62653eb0eaa116a1b8bbc06dd30ab0df73ab8ae16a500c80875e6e6c8a9";

    public String gerarToken(Authentication authentication) {
        UserJpa userJpa = (UserJpa) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("Editora de Livros")
                .setSubject(userJpa.getPessoa().getCpf().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1800000))
                .signWith(SignatureAlgorithm.HS256, senhaForte)
                .compact();
    }

    public Boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(senhaForte).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUsuarioCPF(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(senhaForte)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
//        return new UserJpa(pessoaRepository.findById(cpf).get());
    }

}
