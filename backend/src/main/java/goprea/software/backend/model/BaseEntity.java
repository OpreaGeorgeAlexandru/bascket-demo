package goprea.software.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "BASE_ENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {

    public BaseEntity(){
        id = UUID.randomUUID().toString().substring(0,36);
    }

    @Id
    @Column(name = "ID", columnDefinition = "VARCHAR(36) default UUID()", updatable = false, nullable = false)
    @Size(min = 1, max = 36, message = "ID should be between 1 and 36!")
    private String id;

    @PrePersist
    public void prePersist(){
        if(id == null){
            id = UUID.randomUUID().toString().substring(0,36);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
