package io.github.jonasvlima.libraryapi.repository;

import io.github.jonasvlima.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
    boolean existsByClientId(String clientId);

    List<Client> findByClientIdAndScope(String clientId, String scope);

    List<Client> findByScope(String scope);
}


