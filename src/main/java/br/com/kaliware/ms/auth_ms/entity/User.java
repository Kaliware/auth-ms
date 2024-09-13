package br.com.kaliware.ms.auth_ms.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "tb_user_permission_group",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id")
  )
  private Set<PermissionGroup> permissionGroups = new HashSet<>();

  @OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<OAuthProvider> oauthProviders;

  private Instant deletedAt;

  public User() {}

  public UUID getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public Set<OAuthProvider> getOauthProviders() {
    return oauthProviders;
  }

  public Set<Permission> getAllPermissions() {
    return permissionGroups
        .stream()
        .flatMap(group -> group.getPermissions().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;
    return id.equals(user.id) && email.equals(user.email) && password.equals(user.password) && Objects.equals(permissionGroups, user.permissionGroups) && Objects.equals(oauthProviders, user.oauthProviders) && Objects.equals(deletedAt, user.deletedAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + Objects.hashCode(permissionGroups);
    result = 31 * result + Objects.hashCode(oauthProviders);
    result = 31 * result + Objects.hashCode(deletedAt);
    return result;
  }
}