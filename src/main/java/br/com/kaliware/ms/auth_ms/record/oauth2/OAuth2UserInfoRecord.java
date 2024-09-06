package br.com.kaliware.ms.auth_ms.record.oauth2;

public record OAuth2UserInfoRecord(
    String id,
    String email,
    String name,
    String profilePicture
) {
}