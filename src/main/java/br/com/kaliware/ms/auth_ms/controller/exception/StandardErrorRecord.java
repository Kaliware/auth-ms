package br.com.kaliware.ms.auth_ms.controller.exception;

import java.time.Instant;
import java.util.Collection;

public record StandardErrorRecord(
   Instant timestamp,
   Integer status,
   Collection<String> errors,
   String path

) {

}