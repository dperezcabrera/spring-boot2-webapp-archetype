#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.arch.auth.dtos;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String name;
    private String email;
    private List<String> roles;
}
