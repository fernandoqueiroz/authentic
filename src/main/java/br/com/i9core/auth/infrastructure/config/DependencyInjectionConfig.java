package br.com.i9core.auth.infrastructure.config;

import br.com.i9core.auth.core.ports.ClientApplicationServicePort;
import br.com.i9core.auth.core.usecase.CreateClientApplicationUseCase;
import br.com.i9core.auth.core.usecase.GetClientApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfig {

    @Bean
    CreateClientApplicationUseCase createClientApplicationUseCase(ClientApplicationServicePort clientApplicationServicePort) {
        return new CreateClientApplicationUseCase(clientApplicationServicePort);
    }

    @Bean
    GetClientApplicationUseCase getClientApplicationUseCase(ClientApplicationServicePort clientApplicationServicePort) {
        return new GetClientApplicationUseCase(clientApplicationServicePort);
    }
}
