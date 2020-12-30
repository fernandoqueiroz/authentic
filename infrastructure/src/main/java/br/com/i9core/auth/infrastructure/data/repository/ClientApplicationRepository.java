package br.com.i9core.auth.infrastructure.data.repository;

import br.com.i9core.auth.infrastructure.data.entity.ClientApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientApplicationRepository extends JpaRepository<ClientApplicationEntity, Long> {

    Optional<ClientApplicationEntity> findByClientId(String clientId);

    ClientApplicationEntity findByClientIdAndPlatformId(String clientId, Long platformId);
}

