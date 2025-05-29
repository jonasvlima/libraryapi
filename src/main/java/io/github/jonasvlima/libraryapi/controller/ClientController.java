package io.github.jonasvlima.libraryapi.controller;

import io.github.jonasvlima.libraryapi.controller.dto.ClientDTO;
import io.github.jonasvlima.libraryapi.controller.mappers.ClientMapper;
import io.github.jonasvlima.libraryapi.model.Client;
import io.github.jonasvlima.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody ClientDTO dto){
        Client clientToSave = mapper.toEntity(dto);
        service.salvar(clientToSave);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<ClientDTO> obterPorId(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(client -> ResponseEntity.ok(mapper.toDTO(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody ClientDTO dto) {
        Client clientToUpdate = mapper.toEntity(dto);
        clientToUpdate.setId(UUID.fromString(id));
        service.atualizar(clientToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        UUID clientId = UUID.fromString(id);
        var clientOptional = service.obterPorId(clientId);

        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(clientId);
        return ResponseEntity.noContent().build();
    }
}
