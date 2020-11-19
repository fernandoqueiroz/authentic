package br.com.i9core.auth.core.usecase;

import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.core.exception.client.ClientApplicationAlreadyExistsException;
import br.com.i9core.auth.core.exception.client.ClientApplicationNotFoundException;
import br.com.i9core.auth.core.ports.ClientApplicationServicePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetClientApplicationUseCase {

    ClientApplicationServicePort clientApplicationServicePort;

    public ClientApplication execute(String clientId, Long platformId) throws ClientApplicationNotFoundException {

        ClientApplication clientSearch = clientApplicationServicePort
            .findByClientIdAndPlatformId(clientId, platformId);
        if (clientSearch == null) {
            throw new ClientApplicationNotFoundException();
        }
        return clientSearch;
    }

}
