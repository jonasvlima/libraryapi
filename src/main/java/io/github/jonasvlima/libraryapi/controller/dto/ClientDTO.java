package io.github.jonasvlima.libraryapi.controller.dto;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String clientId,
        String clientSecret,
        String redirectUri,  // u minúsculo
        String scope
) {

}
