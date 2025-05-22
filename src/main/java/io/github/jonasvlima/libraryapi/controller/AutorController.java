package io.github.jonasvlima.libraryapi.controller;

import io.github.jonasvlima.libraryapi.controller.dto.AutorDTO;
import io.github.jonasvlima.libraryapi.controller.dto.ErroResposta;
import io.github.jonasvlima.libraryapi.controller.mappers.AutorMapper;
import io.github.jonasvlima.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.jonasvlima.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.jonasvlima.libraryapi.model.Autor;
import io.github.jonasvlima.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
// http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto){
        try {
            Autor autor = mapper.toEntity(dto);
            autorService.salvar(autor);

//        http://localhost/autores/id
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            autorService.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){

        try {

            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
