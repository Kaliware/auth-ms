package br.com.kaliware.ms.auth_ms.config.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MapperConfiguration {
}
