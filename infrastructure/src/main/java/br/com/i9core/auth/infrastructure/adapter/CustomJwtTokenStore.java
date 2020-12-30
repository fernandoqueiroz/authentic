package br.com.i9core.auth.infrastructure.adapter;

import br.com.i9core.auth.infrastructure.data.entity.ClientApplicationEntity;
import br.com.i9core.auth.infrastructure.data.entity.TokenType;
import br.com.i9core.auth.infrastructure.data.entity.UserEntity;
import br.com.i9core.auth.infrastructure.data.entity.UserTokenEntity;
import br.com.i9core.auth.infrastructure.data.repository.UserTokenRepository;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.time.LocalDateTime;

public class CustomJwtTokenStore extends JwtTokenStore {

    private UserTokenRepository userTokenRepository;

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, UserTokenRepository userTokenRepository) {
        super(jwtTokenEnhancer);
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

        UserTokenEntity userTokenEntity = new UserTokenEntity();
        if (authentication.getPrincipal() instanceof UserEntity) {
            userTokenEntity.setUserId(((UserEntity) authentication.getPrincipal()).getId());
        } else if (authentication.getPrincipal() instanceof ClientApplicationEntity) {
            userTokenEntity.setClientApplicationId(((ClientApplicationEntity) authentication.getPrincipal()).getId());
        }
        userTokenEntity.setClientId(authentication.getOAuth2Request().getClientId());
        userTokenEntity.setJti(String.valueOf(token.getAdditionalInformation().get("jti")));
        userTokenEntity.setType(TokenType.ACCESS_TOKEN);
        userTokenEntity.setCreatedAt(LocalDateTime.now());

        //invalidate all tokens before this for user/client
        userTokenRepository.invalidateTokens(userTokenEntity.getUserId(),
                userTokenEntity.getClientApplicationId(), LocalDateTime.now());

        //save current token
        userTokenRepository.saveAndFlush(userTokenEntity);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {

        OAuth2AccessToken token =  super.readAccessToken(tokenValue);
        if (token.getAdditionalInformation().get("jti") == null
            || userTokenRepository.findByJtiAndExpiration(token.getAdditionalInformation().get("jti").toString(), LocalDateTime.now()) == null) {
            throw new InvalidTokenException("token is expired!");
        }
        return token;
    }

}
