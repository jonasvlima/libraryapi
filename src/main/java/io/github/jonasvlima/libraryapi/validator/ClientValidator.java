package io.github.jonasvlima.libraryapi.validator;

import io.github.jonasvlima.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.jonasvlima.libraryapi.model.Client;
import io.github.jonasvlima.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;

    public void validarNovoClient(Client client) {
        validarCamposObrigatorios(client);
        if (repository.existsByClientId(client.getClientId())) {
            throw new RegistroDuplicadoException("Já existe um cliente com esse clientId!");
        }
    }

    public void validarAtualizacaoClient(Client client) {
        validarCamposObrigatorios(client);
        if (client.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }
    }

    private void validarCamposObrigatorios(Client client) {
        if (!StringUtils.hasText(client.getClientId())) {
            throw new IllegalArgumentException("clientId não pode ser nulo ou vazio.");
        }
        if (!StringUtils.hasText(client.getScope())) {
            throw new IllegalArgumentException("scope não pode ser nulo ou vazio.");
        }
        if (!StringUtils.hasText(client.getClientSecret())) {
            throw new IllegalArgumentException("clientSecret não pode ser nulo ou vazio.");
        }
        if (!StringUtils.hasText(client.getRedirectUri())) {
            throw new IllegalArgumentException("redirectUri não pode ser nulo ou vazio.");
        }
    }
}
