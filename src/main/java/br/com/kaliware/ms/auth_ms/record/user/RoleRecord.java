package br.com.kaliware.ms.auth_ms.record.user;

import java.util.UUID;


public record RoleRecord(
   UUID id,

   String authority
) {
}
