package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import br.senai.sc.editoralivros.security.users.UserJpa;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {
    private final String senhaForte = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

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

    public Cookie gerarCookie(Authentication authentication){
        Cookie cookie = new Cookie("jwt", gerarToken(authentication));
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(5000);
        return cookie;
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
                .getBody().getSubject());
//         new UserJpa(pessoaRepository.findById(cpf).get());
    }

    public String buscarCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request,"jwt");
        if(cookie != null){
            return cookie.getValue();
        }
        throw new RuntimeException("Cookie não encontrado");
    }
}
