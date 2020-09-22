package br.com.i9core.auth.infrastructure.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = 9010733418513700680L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private UserStatus status;

    @Column(name = "platform_id")
    private Long platformId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private List<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles != null
                ? roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_"+ r.getName().toUpperCase()))
                    .collect(Collectors.toList())
                : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserStatus.ACTIVE.equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserStatus.ACTIVE.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserStatus.ACTIVE.equals(status);
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}

