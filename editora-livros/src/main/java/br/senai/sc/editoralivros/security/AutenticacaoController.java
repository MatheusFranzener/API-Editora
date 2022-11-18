package br.senai.sc.editoralivros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired // popula o objeto automaticamente
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Object> autenticacao(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha());

        System.out.println("senha: " + usuarioDTO.getSenha());
        System.out.println("email: " + usuarioDTO.getEmail());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        System.out.println("Autenticação: " + authentication.isAuthenticated());

        if (authentication.isAuthenticated()) {
//            return ResponseEntity.ok().build();
            return ResponseEntity.status(HttpStatus.OK).body(authenticationToken.getPrincipal());
        }

        return ResponseEntity.badRequest().build();
    }


}
