package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private String senhaForte = "05a9e62653eb0eaa116a1b8bbc06dd30ab0df73ab8ae16a500c80875e6e6c8a9";

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Pessoa> pessoaOptional = pessoaRepository.findByEmail(username);
        if (pessoaOptional.isPresent()) {
            return pessoaOptional.get();
        }

        throw new UsernameNotFoundException("Usuário não encontrado!");
    }

    public String gerarToken(Authentication authentication) {
        Pessoa pessoa = (Pessoa) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("Editora de Livros")
                .setSubject(pessoa.getCpf().toString())
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

    public Pessoa getUsuario(String token) {
        Long cpf = Long.parseLong(Jwts.parser().setSigningKey(senhaForte).parseClaimsJws(token).getBody().getSubject());
        return pessoaRepository.findById(cpf).get();
    }

}
