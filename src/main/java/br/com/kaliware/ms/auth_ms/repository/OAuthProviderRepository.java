package br.com.kaliware.ms.auth_ms.repository;


import br.com.kaliware.ms.auth_ms.entity.OAuthProvider;
import br.com.kaliware.ms.auth_ms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OAuthProviderRepository extends JpaRepository<OAuthProvider, UUID> {


}