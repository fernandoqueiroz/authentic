package br.com.i9core.auth.infrastructure.config;

import br.com.i9core.auth.infrastructure.data.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Legacy Authorization Server does not support a custom name for the user parameter, so we'll need
 * to extend the default. By default, it uses the attribute {@code user_name}, though it would be
 * better to adhere to the {@code sub} property defined in the
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT Specification</a>.
 */
public class CustomSubjectUserTokenConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sub", authentication.getName());
        response.put("user_name", authentication.getName());
        //response.put("iat", LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset()));
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserEntity) {
            response.put("platform_id", ((UserEntity) authentication.getPrincipal()).getPlatformId());
        }
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
