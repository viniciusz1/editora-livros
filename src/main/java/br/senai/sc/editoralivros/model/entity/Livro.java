package br.senai.sc.editoralivros.model.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_livros")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @EqualsAndHashCode
public class Livro {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 13, nullable = false, unique = true)
    private Long isbn;

    @Column(length = 50, nullable = false)
    private String titulo;

    @ManyToMany
    @JoinTable(name = "tb_livro_autor",
        joinColumns =
        @JoinColumn(name = "isbn_livro", nullable = false),
        inverseJoinColumns =
        @JoinColumn(name = "cpf_autor", nullable = false))
    private List<Autor> autores;

    @Column(nullable = false)
    private Integer qtdPag;

    @ManyToOne
    @JoinColumn(name = "cpf_revisor")
    private Revisor revisor;

    @Column(length = 50, nullable = false)
    private Integer pagRevisadas = 0;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    private Editora editora;

    @OneToOne(cascade = CascadeType.ALL)
    private Arquivo arquivo;

    public void setArquivo(MultipartFile file) {
        try {
            this.arquivo = new Arquivo(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
