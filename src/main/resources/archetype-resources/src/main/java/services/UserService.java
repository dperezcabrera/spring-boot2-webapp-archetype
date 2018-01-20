#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.dtos.UserDto;
import ${package}.dtos.UserFilterDto;
import java.util.List;

public interface UserService {

    public List<UserDto> listUsers(UserFilterDto filter);

    public UserDto getUser(long userId);
    
    public void addUser(UserDto userDto);
}
