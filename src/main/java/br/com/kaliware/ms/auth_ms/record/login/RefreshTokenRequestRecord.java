package br.com.kaliware.ms.auth_ms.record.login;

import java.util.UUID;

public record RefreshTokenRequestRecord(UUID userId, String refreshToken) {
}