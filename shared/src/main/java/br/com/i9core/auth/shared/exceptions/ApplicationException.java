package br.com.i9core.auth.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Business exception @ Spring Core classes for Spring boot based project
 *
 * @author Fernando Queiroz Fonseca
 * @since 26/08/2020
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApplicationException extends BaseException {

    public ApplicationException(Enum error, Object... parameters) {
        super(error, parameters);
    }
}