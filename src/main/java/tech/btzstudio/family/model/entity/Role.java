package tech.btzstudio.family.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, EntityInterface {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @Column(name ="name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private List<Permission> permissions;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
