package pl.estrix.backend.user.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}),
                @UniqueConstraint(columnNames = {"email"}, name = "uidx_user_email"),
                @UniqueConstraint(columnNames = {"verificationKey"}, name = "uidx_verification_key")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditableEntity {

    @Size(min = 1, max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Size(min = 1, max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Size(min = 1, max = 50)
//    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "{user.invalid.email.format}")
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 80)
    private String password;

    @Column(name = "is_enabled", columnDefinition = "bit(1) default 0")
    private boolean enabled;

    @Column(name = "is_locked", columnDefinition = "bit(1) default 0")
    private boolean locked;

    @Column(name="is_subscribed", columnDefinition = "bit(1) default 0")
    private boolean subscribed;

    @Column(name="verificationKey", length = 64)
    private String verificationKey;

//    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private String role;
//    private Role role;
}
