package io.github.jonasvlima.libraryapi.service;

import io.github.jonasvlima.libraryapi.model.GeneroLivro;
import io.github.jonasvlima.libraryapi.model.Livro;
import io.github.jonasvlima.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        Specification<Livro> specs = null;
        return livroRepository.findAll(specs);
    }
}
