package br.com.i9core.auth.shared.exceptions;

import br.com.i9core.auth.shared.api.format.MessageUtils;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private Enum error;
    private Object[] parameters;

    public BaseException(String message, Object... parameters) {
        super(parseMessage(message, parameters));
    }

    public BaseException(Enum error, Object... parameters) {
        this.error = error;
        this.parameters = parameters;
    }

    protected static String parseMessage(String message, Object... parameters) {
        if (message != null && message.startsWith("{") && message.endsWith("}")) {
            return MessageUtils.getMessage(message.substring(1, message.length()-1), parameters);
        } else {
            return message;
        }
    }
}
