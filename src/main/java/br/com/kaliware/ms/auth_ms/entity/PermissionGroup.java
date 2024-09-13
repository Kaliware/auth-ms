package br.com.kaliware.ms.auth_ms.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_permission_group")
public class PermissionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    // Relacionamento com permissões
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_permission_group_permission",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public PermissionGroup() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionGroup that = (PermissionGroup) o;
        return id.equals(that.id) && name.equals(that.name) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + Objects.hashCode(permissions);
        return result;
    }
}
