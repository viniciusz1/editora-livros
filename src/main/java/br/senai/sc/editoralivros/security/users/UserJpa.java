package br.senai.sc.editoralivros.security.users;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

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
}
