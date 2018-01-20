#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dtos;

import ${package}.architecture.utilities.ToStringUtil;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String groupName;
    private Long groupId;

    @Override
    public String toString() {
        return ToStringUtil.json(this);
    }
}
