package br.com.i9core.auth.infrastructure.adapter;

import br.com.i9core.auth.infrastructure.data.repository.UserRepository;
import br.com.i9core.auth.infrastructure.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAdapter implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("Usuário não existe!", username));
        }
        return userEntity;
    }

    public Page<UserEntity> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
