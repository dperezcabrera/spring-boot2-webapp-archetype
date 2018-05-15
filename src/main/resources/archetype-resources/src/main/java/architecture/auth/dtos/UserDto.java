#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.auth.dtos;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private List<String> roles;
}
