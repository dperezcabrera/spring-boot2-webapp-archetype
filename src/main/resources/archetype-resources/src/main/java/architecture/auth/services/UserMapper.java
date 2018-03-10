#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.auth.services;

import ${package}.architecture.auth.dtos.UserDto;
import ${package}.architecture.auth.entities.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto map(User user, List<String> roles);
}
