package br.com.i9core.auth.core.domain.client;

import lombok.Data;

@Data
public class ClientApplication {

    private Long id;
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String resources;
    private String redirectUris;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private Long platformId;

}
