package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entity.Arquivo;
import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livro;
import br.senai.sc.editoralivros.model.entity.Status;
import br.senai.sc.editoralivros.model.service.LivroService;
import br.senai.sc.editoralivros.util.LivroUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/editora-livros-api/livro")
@Controller
public class LivroController {

    private LivroService livroService;

    @GetMapping("/get/{isbn}")
    public ResponseEntity<List<Livro>> findByIdAndStatus(@PathVariable(value = "isbn") Long isbn) {
        return ResponseEntity.status(HttpStatus.OK).body(
                livroService.findByIsbnAndStatus(isbn, Status.APROVADO));
    }

    @PostMapping
    public ResponseEntity<Object> save(
            @RequestParam("livro") String livroJson,
            @RequestParam("arquivo") MultipartFile file) {
        LivroUtil util = new LivroUtil();
        Livro livro = util.convertJsonToModel(livroJson);
        if (livroService.existsById(livro.getIsbn())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
            "Há um livro com o ISBN " + livro.getIsbn() + " cadastrado.");
        }
        livro.setArquivo(file);
        livro.setStatus(Status.AGUARDANDO_REVISAO);
        return ResponseEntity.status(HttpStatus.OK).body(
                livroService.save(livro));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> findById(
            @PathVariable(value = "isbn") Long isbn) {
        Optional<Livro> livroOptional =  livroService.findById(isbn);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "O livro de ISBN " + isbn + " não foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                livroOptional.get());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Livro>> findByStatus(
            @PathVariable(value = "status") Status status) {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                livroService.findByStatus(status));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> findByAutor(
            @PathVariable(value = "autor") Autor autor) {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                livroService.findByAutor(autor));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                livroService.findAll());
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Livro>> findAllPage(
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                livroService.findAll(pageable));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> deleteById(
            @PathVariable(value = "isbn") Long isbn) {
        if (livroService.existsById(isbn)) {
            livroService.deleteById(isbn);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Livro deletado!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "Livro não encontrado.");
    }
    @PutMapping
    public ResponseEntity<Object> update(
            @RequestBody @Valid LivroDTO livroDto) {
        if (!livroService.existsById(livroDto.getIsbn())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "Livro não encontrado.");
        }
        Livro livroModel = livroService.findById(livroDto.getIsbn()).get();
        BeanUtils.copyProperties(livroDto,livroModel);
        return ResponseEntity.status(HttpStatus.OK).body(
                livroService.save(livroModel));
    }
}
