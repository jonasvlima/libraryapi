package io.github.jonasvlima.libraryapi.service;

import io.github.jonasvlima.libraryapi.model.Autor;
import io.github.jonasvlima.libraryapi.model.GeneroLivro;
import io.github.jonasvlima.libraryapi.model.Livro;
import io.github.jonasvlima.libraryapi.repository.AutorRepository;
import io.github.jonasvlima.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Hyein Grace");
        autor.setNacionalidade("Coreano");
        autor.setDataNascimento(LocalDate.of(2008, 4 , 21 ));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("10987-84874");
        livro.setPreco(BigDecimal.valueOf(99));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("A Casa FLorida");
        livro.setDataPublicacao(LocalDate.of(2025, 1, 5));


        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Grace")){
            throw  new RuntimeException("Rollback!");
        }
    }
}
