package br.com.i9core.auth.core.client;

import br.com.i9core.auth.shared.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientApplicationNotFoundException extends BaseException {

    public ClientApplicationNotFoundException(String clientId) {
        super("{client.application.not.found}", clientId);
    }
}
