package br.com.i9core.auth.infrastructure.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_token")
public class UserTokenEntity {

    public static final String TOKEN_ID = "tokenId";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String AUTHENTICATION_ID = "authenticationId";
    public static final String CLIENT_ID = "clientId";
    public static final String USER_NAME = "username";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String jti;

    @Column
    private TokenType type;

    @Column
    private Long userId;

    @Column
    private Long clientApplicationId;

    @Column
    private String clientId;

    @Column
    private LocalDateTime expiration;

    @Column
    private LocalDateTime createdAt;
}
