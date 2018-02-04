#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.entities.Permission;
import ${package}.entities.PermissionId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {
    
    @Query("select p from Permission p, User u where p.id.user = u.id and u.email = :user")
    List<Permission> findByUser(@Param("user") String user);
}
