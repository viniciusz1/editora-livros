package br.senai.sc.editoralivros.util;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entity.Livro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroUtil {
    private ObjectMapper objectMapper = new ObjectMapper();


    public Livro convertJsonToModel(String livroJson) {
        LivroDTO livroDTO = convertJsonToDto(livroJson);
        return convertDtoToModel(livroDTO);
    }

    private LivroDTO convertJsonToDto(String livroJson){
        try {
            return this.objectMapper.readValue(livroJson, LivroDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private  Livro convertDtoToModel(@Valid LivroDTO livroDTO){
        return this.objectMapper.convertValue(livroDTO, Livro.class);
    }


}
