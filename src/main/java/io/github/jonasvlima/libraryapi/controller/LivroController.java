package io.github.jonasvlima.libraryapi.controller;

import io.github.jonasvlima.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.jonasvlima.libraryapi.controller.dto.ErroResposta;
import io.github.jonasvlima.libraryapi.controller.mappers.LivroMapper;
import io.github.jonasvlima.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.jonasvlima.libraryapi.model.Livro;
import io.github.jonasvlima.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = mapper.toEntity(dto);
            livroService.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
