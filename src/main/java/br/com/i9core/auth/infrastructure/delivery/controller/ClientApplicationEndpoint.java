package br.com.i9core.auth.infrastructure.delivery.controller;

import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.core.usecase.CreateClientApplicationUseCase;
import br.com.i9core.auth.core.usecase.GetClientApplicationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/client-application")
public class ClientApplicationEndpoint {

    @Autowired
    CreateClientApplicationUseCase createClientApplicationUseCase;

    @Autowired
    GetClientApplicationUseCase getClientApplicationUseCase;

    @PostMapping
    public void createClientApplication(@NotNull @RequestBody ClientApplication request) {
        createClientApplicationUseCase.execute(request);
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('user.read')")
    public @ResponseBody ClientApplication getClientApplication(@NotNull @RequestParam String clientId, @NotNull @RequestParam Long platformId) {
        return getClientApplicationUseCase.execute(clientId, platformId);
    }

}

