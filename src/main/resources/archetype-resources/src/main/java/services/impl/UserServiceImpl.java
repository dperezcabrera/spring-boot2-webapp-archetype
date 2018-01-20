#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import ${package}.architecture.permissions.RequiredPermission;
import ${package}.dtos.UserDto;
import ${package}.dtos.UserFilterDto;
import ${package}.entities.User;
import ${package}.mappers.UserMapper;
import ${package}.repositories.UserRepository;
import ${package}.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserDto> listUsers(UserFilterDto filter) {
        return userRepository.findAll().stream().map(UserMapper.MAPPER::map).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long userId) {
        Optional<User> opt = userRepository.findById(userId);
        UserDto result = null;
        if (opt.isPresent()) {
            result = UserMapper.MAPPER.map(opt.get());
        }
        return result;
    }
    
    @Override
    @RequiredPermission(permissions = "supervisor", message = "")
    public void addUser(UserDto userDto) {

    }
}
