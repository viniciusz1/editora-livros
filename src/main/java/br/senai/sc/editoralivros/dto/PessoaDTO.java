package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entity.Genero;
import lombok.Data;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Data
public class PessoaDTO {
//    @NotBlank
    private Long cpf;
//    @NotBlank
    private String nome;
//    @NotBlank
    private String sobrenome;
//    @NotBlank
    private String email;
//    @NotBlank
    private String senha;
//    @NotBlank
    private Genero genero;
}
