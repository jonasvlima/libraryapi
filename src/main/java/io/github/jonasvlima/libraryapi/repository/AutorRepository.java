package io.github.jonasvlima.libraryapi.repository;

import io.github.jonasvlima.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
