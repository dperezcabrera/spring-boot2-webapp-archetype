#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import ${package}.architecture.permissions.PermissionHolder;
import ${package}.architecture.permissions.PermissionAware;
import ${package}.entities.Permission;
import ${package}.repositories.PermissionRepository;
import ${package}.repositories.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.AuditorAware;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PermissionServiceImpl implements PermissionAware<Permission> {

    private UserRepository userRepository;

    private PermissionRepository permissionRepository;
    
    private AuditorAware<String> autidorAware;

    @Override
    public List<PermissionHolder<Permission>> getCurrentPermissions() {
        List<PermissionHolder<Permission>> result = Collections.emptyList();
        Optional<String> opt = autidorAware.getCurrentAuditor();
        if (opt.isPresent()) {
            result = permissionRepository.findByUser(opt.get()).stream().map(p -> new PermissionHolder<>(p.getId().getRole().getName(), p)).collect(Collectors.toList());
        }
        return result;
    }
}

