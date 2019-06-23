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
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PermissionRepository permissionRepository;

	private final UserMapper userMapper;
	
    private List<String> roles(long id) {
        return permissionRepository.findByUserId(id).stream()
                .map(p -> p.getId().getRole().getName())
                .collect(Collectors.toList());
    }

	public UserDto getUser(long userId) {
		return map(userRepository.getOne(userId));
	}

	@Transactional(readOnly = true)
	public Optional<UserDto> getUser(@NonNull String username) {
		return userRepository.findByUsername(username.toLowerCase()).map(this::map);
	}

	private UserDto map(User user) {
		UserDto result = null;
		if (user != null) {
			result = userMapper.map(user, roles(user.getId()));
		}
		return result;
	}
}
