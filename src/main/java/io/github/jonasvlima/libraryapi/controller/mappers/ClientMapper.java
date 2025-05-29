package io.github.jonasvlima.libraryapi.controller.mappers;

import io.github.jonasvlima.libraryapi.controller.dto.ClientDTO;
import io.github.jonasvlima.libraryapi.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client client);

    @Mapping(target = "id", source = "clientDTO.id")
    @Mapping(target = "clientId", source = "clientDTO.clientId")
    @Mapping(target = "clientSecret", source = "clientDTO.clientSecret")
    @Mapping(target = "redirectUri", source = "clientDTO.redirectUri")
    @Mapping(target = "scope", source = "clientDTO.scope")
    Client toEntity(ClientDTO clientDTO);
}
