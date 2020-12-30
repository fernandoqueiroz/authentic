package br.com.i9core.auth.infrastructure.config;

import br.com.i9core.auth.infrastructure.adapter.ClientApplicationServiceAdapter;
import br.com.i9core.auth.infrastructure.adapter.CustomJwtTokenStore;
import br.com.i9core.auth.infrastructure.adapter.UserServiceAdapter;
import br.com.i9core.auth.infrastructure.data.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.PostConstruct;
import java.security.KeyPair;

/**
 * An instance of Legacy Authorization Server (spring-security-oauth2) that uses a single, not-rotating key
 * @author Fernando Queiroz Fonseca
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private KeyPair keyPair;

    @Autowired
    private UserServiceAdapter userDetailsServiceAuth;

    @Autowired
    private ClientApplicationServiceAdapter clientDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    private UserTokenRepository userTokenRepository;

    public AuthorizationServerConfig(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    // http://localhost:8085/oauth/authorize?response_type=code&redirect_uri=http://localhost:8085/me&client_id=client
    @PostConstruct
    public void init() {
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/custom_confirm_access");
        authorizationEndpoint.setErrorPage("forward:/oauth/custom_error");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("isAuthenticated()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .reuseRefreshTokens(true)
                .userDetailsService(userDetailsServiceAuth);
        endpoints.accessTokenConverter(accessTokenConverter());

    }

    @Bean
    public TokenStore tokenStore() {
        CustomJwtTokenStore tokenStore = new CustomJwtTokenStore(accessTokenConverter(), userTokenRepository);
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair);
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomSubjectUserTokenConverter());
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

}
