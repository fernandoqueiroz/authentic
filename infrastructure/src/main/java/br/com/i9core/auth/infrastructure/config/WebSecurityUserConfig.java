package br.com.i9core.auth.infrastructure.config;

import br.com.i9core.auth.infrastructure.adapter.UserServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * For configuring the end users recognized by this Authorization Server
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class WebSecurityUserConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceAdapter userDetailsServiceAuth;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:offs
       http.authorizeRequests()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/v3/api-docs.yml").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
       .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.csrf().disable();
       // @formatter:on
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsServiceAuth;
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

}
