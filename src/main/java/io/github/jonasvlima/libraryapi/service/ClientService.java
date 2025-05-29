package io.github.jonasvlima.libraryapi.service;

import io.github.jonasvlima.libraryapi.model.Client;
import io.github.jonasvlima.libraryapi.repository.ClientRepository;
import io.github.jonasvlima.libraryapi.security.SecurityService;
import io.github.jonasvlima.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;
    private final ClientValidator validator;
    private final SecurityService securityService;

    public Client salvar(Client client){
        validator.validarNovoClient(client);
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        if (!StringUtils.hasText(clientId)) {
            throw new IllegalArgumentException("clientId não pode ser nulo ou vazio");
        }
        return repository.findByClientId(clientId);
    }

    public Optional<Client> obterPorId(UUID id){
        return repository.findById(id);
    }

    public Client atualizar(Client client){
        validator.validarAtualizacaoClient(client);

        Client clientExistente = repository.findById(client.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para atualização"));
        clientExistente.setClientId(client.getClientId());

        if (StringUtils.hasText(client.getClientSecret())){
            clientExistente.setRedirectUri(client.getRedirectUri());
        }
        clientExistente.setRedirectUri(client.getRedirectUri());
        clientExistente.setScope(client.getScope());

        return repository.save(clientExistente);
    }

    public void deletar(UUID id){
        Client client = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para deleção"));

        repository.delete(client);
    }

    public List<Client> pesquisar(String clientId, String scope) {
        if (StringUtils.hasText(clientId) && StringUtils.hasText(scope)) {
            return repository.findByClientIdAndScope(clientId, scope);
        }

        if (StringUtils.hasText(clientId)) {
            Client client = repository.findByClientId(clientId);
            if (client != null) {
                return List.of(client);
            } else {
                return List.of();
            }
        }

        if (StringUtils.hasText(scope)) {
            return repository.findByScope(scope);
        }

        return repository.findAll();
    }

}
