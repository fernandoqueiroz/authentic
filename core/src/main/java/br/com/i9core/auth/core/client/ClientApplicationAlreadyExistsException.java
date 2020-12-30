package br.com.i9core.auth.core.client;

import br.com.i9core.auth.shared.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientApplicationAlreadyExistsException extends BaseException {

    public ClientApplicationAlreadyExistsException(String clientId) {
        super("{client.application.already.exists}", clientId);
    }
}
