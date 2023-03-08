package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entity.Autor;
import lombok.Getter;

import java.util.List;

@Getter
public class LivroDTO {
    private Long isbn;
    private String titulo;
    private List<Autor> autores;
    private Integer qtdPag;
}
