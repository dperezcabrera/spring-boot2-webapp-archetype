#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.architecture.auth.entities.Permission;
import ${package}.architecture.auth.entities.PermissionId;
import ${package}.architecture.auth.entities.Role;
import ${package}.architecture.auth.entities.User;
import ${package}.architecture.auth.repositories.UserRepository;
import ${package}.architecture.auth.repositories.RoleRepository;
import ${package}.architecture.auth.repositories.PermissionRepository;
import ${package}.architecture.auth.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

@Slf4j
@Configuration
@AllArgsConstructor
public class Initializer implements CommandLineRunner {

    private UserService userService;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        roleRepository.save(new Role(null, "Admin" , "Administrator"));
        roleRepository.save(new Role(null, "User" , "User"));
        
        
        userRepository.save(new User(null, "alice", "Alice", "admin", "alice@mail.com"));
        userRepository.save(new User(null, "bob", "Bob", "user", "bob@mail.com"));

        
        permissionRepository.save(new Permission(new PermissionId(userRepository.getOne(1L), roleRepository.getOne(1L))));
        permissionRepository.save(new Permission(new PermissionId(userRepository.getOne(2L), roleRepository.getOne(2L))));
        
        log.info("${symbol_escape}"alice${symbol_escape}" -> {}",userService.getUser("alice"));
    }
}
