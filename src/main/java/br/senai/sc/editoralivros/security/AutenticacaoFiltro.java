package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.security.service.JpaService;
import br.senai.sc.editoralivros.security.users.UserJpa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AutenticacaoFiltro extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private JpaService jpaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login") ||
            request.getRequestURI().equals("/login/auth")){
            filterChain.doFilter(request,response);
            return;
        }
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        } else {
//            token = null;
//        }
        String token = tokenUtils.buscarCookie(request);
        Boolean valido = tokenUtils.validarToken(token);
        if (valido) {
            Long usuarioCPF = tokenUtils.getUsuarioCPF(token);
            UserDetails usuario = jpaService.loadUserByCPF(usuarioCPF);

//            UserDetails usuario = jpaService.loadUserByUsername(usuarioCPF.toString());


            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                            null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(
                    usernamePasswordAuthenticationToken
            );
            filterChain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
