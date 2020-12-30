package br.com.i9core.auth.shared.api.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Json serialization config @ Spring Core classes for Spring boot based project
 * @author Fernando Queiroz Fonseca
 * @since 26/08/2020
 */
@Configuration
public class SerializationConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation( ContentNegotiationConfigurer configurer ) {
        configurer.defaultContentType( MediaType.APPLICATION_JSON );
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        JavaTimeModule dateModule = new JavaTimeModule();
        mapper.registerModule(dateModule);
        return mapper;
    }
}
