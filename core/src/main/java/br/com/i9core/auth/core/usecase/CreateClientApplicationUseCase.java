package br.com.i9core.auth.core.usecase;

import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.core.client.ClientApplicationAlreadyExistsException;
import br.com.i9core.auth.core.ports.ClientApplicationServicePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClientApplicationUseCase {

    ClientApplicationServicePort clientApplicationServicePort;

    public void execute(ClientApplication clientApplication) throws ClientApplicationAlreadyExistsException {

        ClientApplication clientSearch = clientApplicationServicePort
            .findByClientIdAndPlatformId(clientApplication.getClientId(), clientApplication.getPlatformId());
        if (clientSearch != null) {
            throw new ClientApplicationAlreadyExistsException(clientApplication.getClientId());
        }
        clientApplicationServicePort.create(clientApplication);
    }

}
