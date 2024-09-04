package br.com.kaliware.ms.auth_ms.record.login;

public record LoginResponseRecord(String accessToken, Long expiresInSeconds) {
}