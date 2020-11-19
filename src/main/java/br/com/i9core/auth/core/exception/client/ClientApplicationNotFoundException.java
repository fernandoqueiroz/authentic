package br.com.i9core.auth.core.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientApplicationNotFoundException extends RuntimeException{
}
