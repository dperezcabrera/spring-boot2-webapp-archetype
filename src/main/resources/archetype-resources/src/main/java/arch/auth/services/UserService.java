#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.arch.auth.services;

import ${package}.arch.auth.dtos.UserDto;
import ${package}.arch.auth.entities.User;
import ${package}.arch.auth.repositories.PrivilegeRepository;
import ${package}.arch.auth.repositories.RoleRepository;
import ${package}.arch.auth.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final PrivilegeRepository privilegeRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<String> getRoles(@NonNull String username) {
        return roleRepository.findByUsername(username.toLowerCase());
    }

    @Transactional(readOnly = true)
    public List<String> getPrivileges(@NonNull String username) {
        return getPrivileges(username.toLowerCase(), getRoles(username));
    }

    private List<String> getPrivileges(String username, List<String> roles) {
        List<String> privileges = privilegeRepository.findByUsername(username);
        if (!roles.isEmpty()) {
            privileges = Stream.of(privileges, privilegeRepository.findByRoles(roles)).flatMap(List::stream).distinct().collect(Collectors.toList());
        }
        return privileges;
    }

    @Transactional(readOnly = true)
    public UserDto getUser(@NonNull String username) {
        return map(userRepository.getOne(username.toLowerCase()));
    }

    private UserDto map(User user) {
        List<String> roles = getRoles(user.getUsername());
        List<String> privileges = getPrivileges(user.getUsername(), roles);
        return userMapper.map(user, roles, privileges);
    }
}
