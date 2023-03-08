package br.senai.sc.editoralivros.security.service;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import br.senai.sc.editoralivros.security.users.UserJpa;
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
public class JpaService implements UserDetailsService {

    // Essa classe serve para apenas procurar um usuario ( definir que será do banco de dados, diferente do padrão )

    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByEmail(username);

        if (pessoaOptional.isPresent()) {
            return new UserJpa(pessoaOptional.get());
        } else {
            pessoaOptional = pessoaRepository.findById(Long.parseLong(username));

            if(pessoaOptional.isPresent()){

            }
        }

        throw new UsernameNotFoundException("Usuário não encontrado!");
    }

}
