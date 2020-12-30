package br.com.i9core.auth.shared.exceptions;


import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controller for handle with exceptions @ Spring Core classes for Spring boot based project
 *
 * @author Fernando Queiroz Fonseca
 * @since 26/08/2020
 */
@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ValidationExceptionHandler {

    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).body(buildResponseMessage(request, status, ex));
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).body(buildResponseMessage(request, status, ex));
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildResponseMessage(request, HttpStatus.UNAUTHORIZED, ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildResponseMessage(request, HttpStatus.FORBIDDEN, ex));
    }

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<Object> handleAllExceptions(BaseException ex, WebRequest request) {

        HttpStatus httpStatus = ex.getClass().isAnnotationPresent(ResponseStatus.class)
            ? ex.getClass().getAnnotation(ResponseStatus.class).value()
            : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(httpStatus).body(buildResponseMessage(request, httpStatus, ex));
    }

}