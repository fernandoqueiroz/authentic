package br.com.i9core.auth.infrastructure.config;

import br.com.i9core.auth.shared.api.security.AuthenticationEntryPointResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resources";

    @Autowired
    private AuthenticationEntryPointResolver authEntryPointResolver;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true)
            .accessDeniedHandler(authEntryPointResolver)
            .authenticationEntryPoint(authEntryPointResolver);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        String[] webResources = new String[] {
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/actuator/**"
            };
        http.requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers(webResources).permitAll()
            .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
            .antMatchers(HttpMethod.OPTIONS, "/**").access("#oauth2.hasScope('read')")
            .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

}
