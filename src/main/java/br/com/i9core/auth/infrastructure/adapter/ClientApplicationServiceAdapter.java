package br.com.i9core.auth.infrastructure.adapter;

import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.core.ports.ClientApplicationServicePort;
import br.com.i9core.auth.infrastructure.adapter.mapper.ClientApplicationMapper;
import br.com.i9core.auth.infrastructure.data.entity.ClientApplicationEntity;
import br.com.i9core.auth.infrastructure.data.repository.ClientApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientApplicationServiceAdapter implements ClientDetailsService, ClientApplicationServicePort {

    @Autowired
    private ClientApplicationRepository clientApplicationRepository;

    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other reason.
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Optional<ClientApplicationEntity> clientApplicationEntity = clientApplicationRepository.findByClientId(clientId);
        return clientApplicationEntity.orElseThrow(() -> new ClientRegistrationException("Client not found with id "+ clientId));
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<ClientApplication> find(Long id) {
        return Optional.empty();
    }

    @Override
    public ClientApplication findByClientIdAndPlatformId(String clientId, Long platformId) {
        return ClientApplicationMapper.getInstance()
            .from(clientApplicationRepository.findByClientIdAndPlatformId(clientId, platformId));
    }

    @Override
    public ClientApplication create(ClientApplication clientApplication) {
        ClientApplicationMapper mapper = ClientApplicationMapper.getInstance();
        return mapper.from(clientApplicationRepository.save(mapper.to(clientApplication)));
    }

    @Override
    public List<ClientApplication> list() {
        return null;
    }
}
