package br.com.i9core.auth.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

}
