package io.github.jonasvlima.libraryapi.service;

import io.github.jonasvlima.libraryapi.model.Livro;
import io.github.jonasvlima.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
