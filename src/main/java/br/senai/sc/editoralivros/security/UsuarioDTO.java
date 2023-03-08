package br.senai.sc.editoralivros.security;

import lombok.Data;
import lombok.NonNull;

@Data
public class UsuarioDTO {
    @NonNull
    private String email;
    @NonNull
    private String senha;
}
