package br.com.i9core.auth.shared.exceptions;

import br.com.i9core.auth.shared.api.model.ResponseMessage;
import br.com.i9core.auth.shared.api.model.ResponseMessageDetail;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handle with validation errors @ Spring Core classes for Spring boot based project
 * @author Fernando Queiroz Fonseca
 * @since 26/08/2020
 */
@Log4j2
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(request, ex);
        return new ResponseEntity(buildErrorMessage(ex.getBindingResult(), request), headers, status);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(request, ex);
        return new ResponseEntity<>(buildErrorMessage(ex.getBindingResult(), request), headers, status);
    }

    protected ResponseMessage buildErrorMessage(BindingResult bindingResult, WebRequest request) {

        List<ResponseMessageDetail> validationErrors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.stream().forEach(fieldError -> {
                validationErrors.add(ResponseMessageDetail
                    .builder()
                    .error(fieldError.getField().toUpperCase() + "_INVALID")
                    .message(fieldError.getDefaultMessage())
                    .build()
                );
            });
        }
        return buildResponseMessage(request, HttpStatus.BAD_REQUEST, validationErrors);
    }

    protected ResponseMessage buildResponseMessage(WebRequest request,
                                                   HttpStatus httpStatus,
                                                   Throwable t) {
        return buildResponseMessage(request, httpStatus,
            Arrays.asList(ResponseMessageDetail.builder()
                .message(t.getLocalizedMessage())
                .error(httpStatus.name())
                .build()
            ));
    }

    protected ResponseMessage buildResponseMessage(WebRequest request,
                                                   HttpStatus httpStatus,
                                                   BaseException ex) {
        return buildResponseMessage(request, httpStatus,
            Arrays.asList(ResponseMessageDetail.builder()
                .error(ex.getError() != null ? ex.getError().name() : httpStatus.name())
                .message(ex.getLocalizedMessage())
                .build()
            ));
    }

    protected ResponseMessage buildResponseMessage(WebRequest request,
                                                   HttpStatus httpStatus,
                                                   List<ResponseMessageDetail> errors) {
        return ResponseMessage.builder()
            .path(((ServletWebRequest)request).getRequest().getRequestURI())
            .status(httpStatus.value())
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .build();
    }
}
