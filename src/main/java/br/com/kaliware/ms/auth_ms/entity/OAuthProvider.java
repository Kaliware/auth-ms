package br.com.kaliware.ms.auth_ms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_oauth_provider")
public class OAuthProvider {

  @EmbeddedId
  private OAuthProviderId id;

  @Column(name = "provider_user_id", nullable = false)
  private String providerUserId;

  public OAuthProvider() {}

  public OAuthProviderId getId() {
    return id;
  }

  public String getProviderUserId() {
    return providerUserId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OAuthProvider that = (OAuthProvider) o;
    return id.equals(that.id) && providerUserId.equals(that.providerUserId);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + providerUserId.hashCode();
    return result;
  }

  @Embeddable
  public static class OAuthProviderId {

    @ManyToOne
    private User user;

    @Column(name = "provider", nullable = false)
    private Integer provider;

    public OAuthProviderId() {
    }

    public User getUser() {
      return user;
    }

    public Integer getProvider() {
      return provider;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      OAuthProviderId that = (OAuthProviderId) o;
      return user.equals(that.user) && provider.equals(that.provider);
    }

    @Override
    public int hashCode() {
      int result = user.hashCode();
      result = 31 * result + provider.hashCode();
      return result;
    }

  }
}