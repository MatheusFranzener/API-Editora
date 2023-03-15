package br.senai.sc.editoralivros.security.users;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserJpa implements UserDetails {

    // Essa classe serve para fazer login no sistema, faz uma conexão com a classe JpaService

    private Pessoa pessoa;

    private Collection<GrantedAuthority> authorities;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public UserJpa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String getPassword() {
        return pessoa.getSenha();
    }

    @Override
    public String getUsername() {
        return pessoa.getEmail();
    }

    // Substituindo o padrão ( que retorna nulo ), pega o nome da classe do objeto pessoa
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getPessoa().getClass().getSimpleName()));

        return authorities;
    }
}
