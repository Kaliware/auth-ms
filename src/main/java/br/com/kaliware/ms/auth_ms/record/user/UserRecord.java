package br.com.kaliware.ms.auth_ms.record.user;

import java.util.Set;
import java.util.UUID;

public record UserRecord(
    UUID id,

    String email,

    String password,

    Set<RoleRecord> roles
) {
}
