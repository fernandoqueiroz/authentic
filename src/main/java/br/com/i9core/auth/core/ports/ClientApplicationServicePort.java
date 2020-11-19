package br.com.i9core.auth.core.ports;

import br.com.i9core.auth.core.domain.client.ClientApplication;

import java.util.List;
import java.util.Optional;

public interface ClientApplicationServicePort {

    void remove(Long id);

    Optional<ClientApplication> find(Long id);

    ClientApplication findByClientIdAndPlatformId(String clientId, Long platformId);

    ClientApplication create(ClientApplication clientApplication);

    List<ClientApplication> list();
}
