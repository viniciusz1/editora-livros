package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.security.service.JpaService;
import br.senai.sc.editoralivros.security.users.UserJpa;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class AutenticacaoController {

    private TokenUtils tokenUtils = new TokenUtils();
    @Autowired
    private JpaService jpaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<Object> autenticacao(
            @RequestBody @Valid UsuarioDTO usuarioDTO,
            HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),usuarioDTO.getSenha());

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        System.out.println(authentication.isAuthenticated());

        if (authentication.isAuthenticated()) {
            response.addCookie(tokenUtils.gerarCookie(authentication));
            UserJpa userJpa = (UserJpa) authentication.getPrincipal();
            return ResponseEntity.ok(userJpa.getPessoa());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
