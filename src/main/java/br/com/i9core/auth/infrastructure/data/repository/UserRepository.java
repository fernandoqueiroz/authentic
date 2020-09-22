package br.com.i9core.auth.infrastructure.data.repository;

import br.com.i9core.auth.infrastructure.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
