#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.auth.services;

import ${package}.architecture.auth.dtos.UserDto;

public interface UserService {

    public UserDto getUser(long userId);

    public UserDto getUser(String username);
}
