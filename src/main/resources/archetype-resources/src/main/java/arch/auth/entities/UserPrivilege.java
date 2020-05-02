#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.arch.auth.entities;

import ${package}.arch.common.AuditableEntity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_privileges")
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivilege extends AuditableEntity implements Serializable {

    @EmbeddedId
    private UserPrivilegeId id;
}
