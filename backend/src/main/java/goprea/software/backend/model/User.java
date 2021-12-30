package goprea.software.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "USER", schema = "auth")
public class User extends BaseEntity{

    @Column(name = "NAME")
    @NotNull(message = "Name cannot be null!")
    @Size(min = 2, max = 50, message = "Invalid first name length!")
    private String name;

    @Column(name = "USERNAME",unique = true)
    @NotNull(message = "Username cannot be null!")
    @Size(min = 2, max = 50, message = "Invalid username length!")
    private String username;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Transient
    private String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUsername(), getPassword(), getToken());
    }
}
