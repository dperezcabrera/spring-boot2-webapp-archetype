#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.arch.auth.services;

import ${package}.arch.auth.dtos.UserDto;
import ${package}.arch.auth.entities.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user, List<String> roles, List<String> privileges);
}
