package br.com.i9core.auth.infrastructure.data.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "client_application")
public class ClientApplicationEntity implements ClientDetails {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "grant_types")
    private String grantTypes;

    @Column(name = "scope")
    private String scopes;

    @Column(name = "resource_ids")
    private String resources;

    @Column(name = "redirect_uri")
    private String redirectUris;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "platform_id")
    private Long platformId;

    /**
     * The client id.
     *
     * @return The client id.
     */
    @Override
    public String getClientId() {
        return clientId;
    }

    /**
     * The resources that this client can access. Can be ignored by callers if empty.
     *
     * @return The resources of this client.
     */
    @Override
    public Set<String> getResourceIds() {
        return resources != null ? new HashSet<>(Arrays.asList(resources.split(","))) : null;
    }

    /**
     * Whether a secret is required to authenticate this client.
     *
     * @return Whether a secret is required to authenticate this client.
     */
    @Override
    public boolean isSecretRequired() {
        return clientSecret != null;
    }

    /**
     * The client secret. Ignored if the {@link #isSecretRequired() secret isn't required}.
     *
     * @return The client secret.
     */
    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    public boolean isScoped() {
        return false;
    }

    /**
     * The scope of this client. Empty if the client isn't scoped.
     *
     * @return The scope of this client.
     */
    @Override
    public Set<String> getScope() {
        return scopes != null ? new HashSet<>(Arrays.asList(scopes.split(","))) : null;
    }

    /**
     * The grant types for which this client is authorized.
     *
     * @return The grant types for which this client is authorized.
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return grantTypes != null ? new HashSet<>(Arrays.asList(grantTypes.split(","))) : null;
    }

    /**
     * The pre-defined redirect URI for this client to use during the "authorization_code" access grant. See OAuth spec,
     * section 4.1.1.
     *
     * @return The pre-defined redirect URI for this client.
     */
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return redirectUris != null ? new HashSet<>(Arrays.asList(redirectUris.split(","))) : null;
    }

    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
     * Note that these are NOT the authorities that are granted to the user with an authorized access token.
     * Instead, these authorities are inherent to the client itself.
     *
     * @return the authorities (never <code>null</code>)
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * The access token validity period for this client. Null if not set explicitly (implementations might use that fact
     * to provide a default value for instance).
     *
     * @return the access token validity period
     */
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    /**
     * The refresh token validity period for this client. Null for default value set by token service, and
     * zero or negative for non-expiring tokens.
     *
     * @return the refresh token validity period
     */
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    /**
     * Test whether client needs user approval for a particular scope.
     *
     * @param scope the scope to consider
     * @return true if this client does not need user approval
     */
    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
     * for storing descriptive information.
     *
     * @return a map of additional information
     */
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
