package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.service.GoogleService;
import br.senai.sc.editoralivros.security.service.JpaService;
import br.senai.sc.editoralivros.security.users.UserJpa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@AllArgsConstructor
public class AutenticacaoConfig {

    private JpaService jpaService;

    private GoogleService googleService;

    // Utilizado para substituir o provider
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // Configura as autorizações de acesso http
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/editoralivros/login", "/editoralivros/login/auth", "/editoralivros/logout").permitAll()
//                Post do livro somente para o autor
                .antMatchers(HttpMethod.POST, "/editoralivros/livro")
                .hasAnyAuthority("Autor")
                .anyRequest().authenticated();

        httpSecurity.csrf().disable()
                .cors().disable();

        httpSecurity.formLogin().permitAll()
                .and().logout().permitAll();

        // Cria uma política de sessão ( usuário não fique autenticado )
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Faz com que toda vez que há uma requisição passe antes pelo filtro
        httpSecurity.addFilterBefore(new AutenticacaoFiltro(new TokenUtils(), jpaService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

}
