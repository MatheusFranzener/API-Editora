package br.senai.sc.editoralivros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AutenticacaoConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    // Configura as autorizações de acesso

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                // libera o acesso sem autenticação para o /login
                .antMatchers("/login", "/editoralivos/pessoa").permitAll()
                .antMatchers(HttpMethod.POST, "/editoralivos/pessoa").permitAll()
                // determina que todas as demais requisicoes terao de ser autenticadas
                .anyRequest().permitAll()
//                .and().formLogin()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // Configura a autenticação para os acessos

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
