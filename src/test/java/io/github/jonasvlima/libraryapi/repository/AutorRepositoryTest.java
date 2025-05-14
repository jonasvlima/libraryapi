package io.github.jonasvlima.libraryapi.repository;

import io.github.jonasvlima.libraryapi.model.Autor;
import io.github.jonasvlima.libraryapi.model.GeneroLivro;
import io.github.jonasvlima.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("5fe5b281-ff05-4b03-bbb5-a5f3c20559e0");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("56bb45a4-b718-4bec-b5a2-62e25f9d86ef");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("5df21f9d-32cb-476f-b43e-5d1551f294c7");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Kang Haerin");
        autor.setNacionalidade("Coreano");
        autor.setDataNascimento(LocalDate.of(2006, 5, 15));

        Livro livro = new Livro();
        livro.setIsbn("25387-84874");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Pequena Batatinha");
        livro.setDataPublicacao(LocalDate.of(2024, 3, 23));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("50238-94874");
        livro2.setPreco(BigDecimal.valueOf(300));
        livro2.setGenero(GeneroLivro.ROMANCE);
        livro2.setTitulo("Estrelas Brilham");
        livro2.setDataPublicacao(LocalDate.of(2025, 3, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

//        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("fd45d81d-816f-43c5-b409-0e8b12a0e6c8");
        var autor = repository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
