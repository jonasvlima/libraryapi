package io.github.jonasvlima.libraryapi.controller.mappers;

import io.github.jonasvlima.libraryapi.controller.dto.UsuarioDTO;
import io.github.jonasvlima.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
