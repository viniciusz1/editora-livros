package br.senai.sc.editoralivros.security.users;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserJpa implements UserDetails {

    private Pessoa pessoa;

    private Collection<GrantedAuthority> authorities;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public UserJpa(Pessoa pessoa){
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

    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        //quando for implementado no low, fazer com enum
        //colocar na pessoa uma lista de granted authorities
        authorities.add(new SimpleGrantedAuthority(
                this.getPessoa().getClass().getSimpleName()
        ));
        return authorities;
    }
}
