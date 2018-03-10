#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.auth.services;

import ${package}.architecture.auth.dtos.UserDto;
import ${package}.architecture.auth.entities.User;
import ${package}.architecture.auth.repositories.PermissionRepository;
import ${package}.architecture.auth.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceBase implements UserService {

    private UserRepository userRepository;

    private PermissionRepository permissionRepository;

    private List<String> roles(long id) {
        return permissionRepository.findByUserId(id).stream()
                .map(p -> p.getId().getRole().getName())
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long userId) {
        return getUser(userRepository.findById(userId));
    }

    @Override
    public UserDto getUser(@NonNull String username) {
        return getUser(userRepository.findByUsername(username));
    }

    private UserDto getUser(Optional<User> opt) {
        UserDto result = null;
        if (opt.isPresent()) {
            User usuario = opt.get();
            result = UserMapper.MAPPER.map(usuario, roles(usuario.getId()));
        }
        return result;
    }
}
