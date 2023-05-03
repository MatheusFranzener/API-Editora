package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.service.GoogleService;
import br.senai.sc.editoralivros.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Classe para fazer a configuração gerar do autentication

@Configuration
@AllArgsConstructor
public class AutenticacaoConfig {

    private JpaService jpaService;

    private GoogleService googleService;

    // Seta o serviço de autenticação e a criptografia de senha

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // Método de configuração do cors
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Permite o link da aplicação
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));

        // Descreve quais métodos serão permitidos
        corsConfiguration.setAllowedMethods(List.of("POST", "DELETE", "GET", "PUT"));

        // Permite que salve um cookie da api ou capturar o cookie
        corsConfiguration.setAllowCredentials(true);

        // Permite que o header seja qualquer um
        corsConfiguration.setAllowedHeaders(List.of("*"));

        // Qualquer caminho que fizer requisição deverá utilizar essas configurações do corsConfiguration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    // Configura as autorizações de acesso http
    // requestMatchers ( se não der pra colocar o antMatchers )
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/editoralivros/login", "/editoralivros/login/auth", "/editoralivros/logout").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
//                Post do livro somente para o autor
                .antMatchers(HttpMethod.POST, "/editoralivros/livro")
                .hasAuthority("Autor")
                .anyRequest().authenticated();

        httpSecurity.csrf().disable();

        httpSecurity.cors().configurationSource(corsConfigurationSource());

        httpSecurity.logout().permitAll();

        // Cria uma política de sessão ( usuário não fique autenticado )
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Faz com que toda vez que há uma requisição passe antes pelo filtro
        httpSecurity.addFilterBefore(new AutenticacaoFiltro(new TokenUtils(), jpaService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // Injeção de dependências no AutenticacaoController quando necessitar (autowired não funcionar)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

}
