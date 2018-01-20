#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entities;

import  ${package}.architecture.utilities.ToStringUtil;
import  ${package}.architecture.entities.AuditableEntity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AuditableEntity implements Serializable {

    @EmbeddedId
    private PermissionId id;

    @Override
    public String toString() {
        return ToStringUtil.json(this);
    }
}
