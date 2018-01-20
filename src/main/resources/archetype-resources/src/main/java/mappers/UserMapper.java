#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mappers;

import ${package}.dtos.UserDto;
import ${package}.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(source = "group.name", target = "groupName")
        ,@Mapping(source = "group.id", target = "groupId")})
    UserDto map(User user);
}
