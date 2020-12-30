package br.com.i9core.auth.core.usecase;

import br.com.i9core.auth.core.client.ClientApplicationNotFoundException;
import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.core.ports.ClientApplicationServicePort;
import br.com.i9core.auth.shared.exceptions.NotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetClientApplicationUseCase {

    ClientApplicationServicePort clientApplicationServicePort;

    public ClientApplication execute(String clientId, Long platformId) throws NotFoundException {

        ClientApplication clientSearch = clientApplicationServicePort
            .findByClientIdAndPlatformId(clientId, platformId);
        if (clientSearch == null) {
            throw new ClientApplicationNotFoundException(clientId);
        }
        return clientSearch;
    }

}
