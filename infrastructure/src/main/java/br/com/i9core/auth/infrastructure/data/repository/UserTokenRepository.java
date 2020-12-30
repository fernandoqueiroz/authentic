package br.com.i9core.auth.infrastructure.data.repository;

import br.com.i9core.auth.infrastructure.data.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {

    @Modifying
    @Transactional
    @Query("update UserTokenEntity set expiration = :expiration where (:userId is null or userId = :userId) "
            + " and (:clientApplicationId is null or clientApplicationId = :clientApplicationId) "
            + " and (expiration is null or expiration > :expiration) ")
    void invalidateTokens(@Param("userId") Long userId,
                          @Param("clientApplicationId") Long clientApplicationId,
                          @Param("expiration") LocalDateTime expiration);

    @Query("select u from UserTokenEntity u where u.jti = :jti and (u.expiration is null or u.expiration > :expiration)")
    UserTokenEntity findByJtiAndExpiration(@Param("jti") String jti, @Param("expiration") LocalDateTime expiration);
}
