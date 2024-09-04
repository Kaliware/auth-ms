package br.com.kaliware.ms.auth_ms.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String authority;

  public Role() {
  }

  public Role(UUID id, String authority) {
    this.id = id;
    this.authority = authority;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Role role)) return false;
    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


}