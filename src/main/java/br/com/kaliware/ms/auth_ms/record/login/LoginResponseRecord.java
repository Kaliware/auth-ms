package br.com.kaliware.ms.auth_ms.record.login;

public record LoginResponseRecord(String accessToken, String refreshToken, Long expiresInSeconds) {
}