#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.security;

import ${package}.architecture.auth.repositories.PermissionRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("roleChecker")
@AllArgsConstructor
public class RoleChecker {

    private final PermissionRepository permissionRepository;

    private final AuditorAware<String> autidorAware;

    @Transactional(readOnly = true)
    public boolean hasRole(String role) {
        boolean result = false;
        Optional<String> opt = autidorAware.getCurrentAuditor();
        if (opt.isPresent()) {
            result = permissionRepository.findByUsername(opt.get()).stream().anyMatch(p -> p.getId().getRole().getName().equals(role));
        } 
        return result;
    }
    
    @Transactional(readOnly = true)
    public boolean hasAnyRole(String... roles) {
        boolean result = false;
        Optional<String> opt = autidorAware.getCurrentAuditor();
        if (opt.isPresent() && roles != null) {
            Set<String> rolesSet = new HashSet<>(Arrays.asList(roles));
            result = permissionRepository.findByUsername(opt.get()).stream().anyMatch(p -> rolesSet.contains(p.getId().getRole().getName()));
        } 
        return result;
    }
}
