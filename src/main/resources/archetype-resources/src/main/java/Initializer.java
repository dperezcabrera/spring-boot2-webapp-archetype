#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.dtos.UserDto;
import ${package}.entities.Group;
import ${package}.entities.Permission;
import ${package}.entities.PermissionId;
import ${package}.entities.Role;
import ${package}.entities.User;
import ${package}.repositories.GroupRepository;
import ${package}.repositories.UserRepository;
import ${package}.repositories.RoleRepository;
import ${package}.repositories.PermissionRepository;
import ${package}.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

@Slf4j
@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class Initializer implements CommandLineRunner {

    private UserService userService;
    
    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private GroupRepository groupRepository;

    private PermissionRepository permissionRepository;
    
    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        roleRepository.save(new Role(null, "receptor"));
        roleRepository.save(new Role(null, "supervisor"));
        groupRepository.save(new Group(null, "group 1"));
        groupRepository.save(new Group(null, "group 2"));
        groupRepository.save(new Group(null, "group 3"));
        groupRepository.save(new Group(null, "group 4"));
        
        List<Group> groups = groupRepository.findAll();
        
        userRepository.save(new User(null, "Alice", "alice@email.org", groups.get(0)));
        userRepository.save(new User(null, "Bob", "bob@email.org", groups.get(1)));
        
        permissionRepository.save(new Permission(new PermissionId(userRepository.getOne(1L), groups.get(2), roleRepository.getOne(2L))));
        permissionRepository.save(new Permission(new PermissionId(userRepository.getOne(1L), groups.get(3), roleRepository.getOne(1L))));
        permissionRepository.save(new Permission(new PermissionId(userRepository.getOne(2L), groups.get(3), roleRepository.getOne(1L))));

        log.info("\nsearch by user 1");
        groupRepository.findByUserRole(userRepository.getOne(1L), roleRepository.findAll()).stream().map(t -> t.toString()).forEach(log::info);
        
        log.info("\nsearch by user 2");
        groupRepository.findByUserRole(userRepository.getOne(2L), roleRepository.findAll()).stream().map(t -> t.toString()).forEach(log::info);      
    }
}
