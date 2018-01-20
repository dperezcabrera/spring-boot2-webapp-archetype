#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.entities.Role;
import ${package}.entities.Group;
import ${package}.entities.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.id in (Select p.id.group from Permission p where p.id.user = :user and p.id.role in :roles)")
    List<Group> findByUserRole(@Param("user") User user, @Param("roles") Collection<Role> roles);
}
