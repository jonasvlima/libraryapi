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
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    /// livro (titulo,..., nome_arquivo) -> id.png
    @Transactional
    public void salvarLivroComFoto(){
        // salva o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("2bcb9c7a-8623-4703-927b-537eaf1e069e"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2009, 6, 1));

    }

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
