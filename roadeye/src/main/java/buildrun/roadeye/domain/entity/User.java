package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.rest.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private RoleEnum role;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String phone;
    private String photo;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (RoleEnum.contains(this.role.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.toString().toUpperCase()));
            if (this.role == RoleEnum.ADMIN) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));
                authorities.add(new SimpleGrantedAuthority("ROLE_RESPONSIBLE"));
                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            }
        } else {
            throw new IllegalArgumentException("Role " + this.role + "is not valid.");
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
