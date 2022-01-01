package tech.btzstudio.family.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "permission")
public class Permission implements EntityInterface {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
