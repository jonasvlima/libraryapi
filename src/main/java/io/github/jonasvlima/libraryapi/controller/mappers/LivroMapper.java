package io.github.jonasvlima.libraryapi.controller.mappers;

import io.github.jonasvlima.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.jonasvlima.libraryapi.model.Livro;
import io.github.jonasvlima.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);
}
