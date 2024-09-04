package br.com.kaliware.ms.auth_ms.mapper;


import br.com.kaliware.ms.auth_ms.config.mapper.MapperConfiguration;
import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.user.UserRecord;
import br.com.kaliware.ms.auth_ms.service.bcrypt.PasswordService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class, uses = {PasswordService.class})
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  UserRecord entityToRecord(User entity);

}
