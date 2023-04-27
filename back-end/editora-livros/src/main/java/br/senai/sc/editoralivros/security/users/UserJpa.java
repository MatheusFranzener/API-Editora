package br.senai.sc.editoralivros.security.users;

import br.senai.sc.editoralivros.model.entities.Pessoa;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// A classe UserJPA é a classe utilizada para realizar a autenticação do usuário ( faz uma ligação com pessoa e userDetails )
@Data
public class UserJpa implements UserDetails {

    // Implementação da (Pessoa/Usuario) do sistema

    private Pessoa pessoa;

    // Depois a implementação de todos os métodos do UserDetails
    // Lembrar de mudar para true e depois colocar o getSenha e getEmail

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

    // Substituindo o padrão ( que retorna nulo ), ele retorna o tipo da pessoa ( autor, diretor ou revisor )

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getPessoa().getClass().getSimpleName()));

        return authorities;
    }
}
