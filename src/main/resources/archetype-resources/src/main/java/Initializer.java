#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.arch.auth.entities.Privilege;
import ${package}.arch.auth.entities.Role;
import ${package}.arch.auth.entities.RolePrivilege;
import ${package}.arch.auth.entities.RolePrivilegeId;
import ${package}.arch.auth.entities.User;
import ${package}.arch.auth.entities.UserPrivilege;
import ${package}.arch.auth.entities.UserPrivilegeId;
import ${package}.arch.auth.entities.UserRole;
import ${package}.arch.auth.entities.UserRoleId;
import ${package}.arch.auth.repositories.UserRepository;
import ${package}.arch.auth.repositories.RoleRepository;
import ${package}.arch.auth.repositories.PrivilegeRepository;
import ${package}.arch.auth.repositories.RolePrivilegeRepository;
import ${package}.arch.auth.repositories.UserPrivilegeRepository;
import ${package}.arch.auth.repositories.UserRoleRepository;
import ${package}.arch.auth.services.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

    private UserRoleRepository userRoleRepository;

    private PrivilegeRepository privilegeRepository;

    private RolePrivilegeRepository rolePrivilegeRepository;

    private UserPrivilegeRepository userPrivilegeRepository;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        log.info("Initializer run ..");
        Privilege writeUserPrivilege = privilegeRepository.save(new Privilege("WRITE_USER"));
        Privilege readUserPrivilege = privilegeRepository.save(new Privilege("READ_USER"));
        Privilege operationPrivilege = privilegeRepository.save(new Privilege("OPERATION"));
        Role adminRole = roleRepository.save(new Role("admin"));
        Role userRole = roleRepository.save(new Role("user"));

        User admin = userRepository.save(new User("alice", "Alice", "alice@mail.com"));
        List<User> users = Arrays.asList(new User("bob", "Bob", "bob@mail.com"));
        users = users.stream().map(userRepository::save).collect(Collectors.toList());
        rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(adminRole, writeUserPrivilege)));
        rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(adminRole, readUserPrivilege)));
        rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(userRole, readUserPrivilege)));
        userPrivilegeRepository.save(new UserPrivilege(new UserPrivilegeId(admin, operationPrivilege)));
        userRoleRepository.save(new UserRole(new UserRoleId(admin, adminRole)));
        users.forEach(u -> userRoleRepository.save(new UserRole(new UserRoleId(u, userRole))));
        log.info("${symbol_escape}"admin${symbol_escape}" -> {}", userService.getUser(admin.getUsername()));
        log.info("Initializer ends");
    }
}
