package io.github.jonasvlima.libraryapi.repository;

import io.github.jonasvlima.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
