package effective.mobile.test.user.entity;

import effective.mobile.test.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Schema(description = "Entity representing a user in the system")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @Column(name = "password", nullable = false)
    @Schema(description = "Hashed password of the user", example = "$2a$10$12345abcdef")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "Email address of the user", example = "johndoe@gmail.com")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Schema(description = "Role of the user", example = "ROLE_ADMIN")
    private Constants.Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
}