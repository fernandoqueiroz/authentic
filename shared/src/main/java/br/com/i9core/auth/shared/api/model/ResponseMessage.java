package br.com.i9core.auth.shared.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Default Message response for generate Json Payload in validation and Errors
 * @author Fernando Queiroz Fonseca
 * @since 29/05/2020
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage {

    private String path;
    private Integer status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<ResponseMessageDetail> errors = new ArrayList<>();

}
