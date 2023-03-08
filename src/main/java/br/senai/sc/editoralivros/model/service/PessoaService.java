package br.senai.sc.editoralivros.model.service;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    public boolean existsById(Long cpf) {
        return pessoaRepository.existsById(cpf);
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public <S extends Pessoa> S save(S entity) {
        return pessoaRepository.save(entity);
    }

    public Optional<Pessoa> findById(Long cpf) {
        return pessoaRepository.findById(cpf);
    }

    public void deleteById(Long cpf) {
        pessoaRepository.deleteById(cpf);
    }

    public Optional<Pessoa> findByEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return pessoaRepository.existsByEmail(email);
    }
}
