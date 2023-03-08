package br.senai.sc.editoralivros.repository;

import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livro;
import br.senai.sc.editoralivros.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {
    List<Livro> findByStatus(Status status);
    List<Livro> findByAutores(Autor autor);
    List<Livro> findByIsbnAndStatus(Long isbn, Status status);
}
