#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import ${package}.architecture.common.ReflectionUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PermissionMethodValidationInterceptor implements MethodInterceptor {

    private static final String ANY_OF_PREFIX = "anyOf:";
    private static final String ALL_OF_PREFIX = "allOf:";

    private PermissionAware permissionAware;

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Optional<RequiredPermission> opt = ReflectionUtil.getAnnotation(mi.getMethod(), mi.getThis().getClass(), RequiredPermission.class);
        if (opt.isPresent()) {
            List<PermissionHolder> prevPermissions = PermissionContextHolder.getFastPermissions();
            RequiredPermission requiredPermission = opt.get();
            List<PermissionHolder> permissions = (prevPermissions == null) ? (List<PermissionHolder>) permissionAware.getCurrentPermissions() : prevPermissions;
            RoleCondition condition = buildFromString(requiredPermission.permissions());
            if (!condition.check(permissions)) {
                throw new DeniedPermissionException(requiredPermission.message(), requiredPermission.permissions());
            }
            PermissionContextHolder.set(permissions);
            try {
                return mi.proceed();
            } finally {
                PermissionContextHolder.set(prevPermissions);
            }
        } else {
            return mi.proceed();
        }
    }

    private static interface RoleCondition {

        public boolean check(List<PermissionHolder> permissions);
    }

    @AllArgsConstructor
    private static class AnyRoleCondition implements RoleCondition {

        private Set<String> roles;

        @Override
        public boolean check(List<PermissionHolder> permissions) {
            return permissions.stream().anyMatch(p -> roles.contains(p.getName()));
        }
    }

    @AllArgsConstructor
    private static class AllRolesCondition implements RoleCondition {

        private Set<String> roles;

        @Override
        public boolean check(List<PermissionHolder> permissions) {
            Set<String> userRoles = permissions.stream().map(p -> p.getName()).collect(Collectors.toSet());
            return !roles.stream().anyMatch(r -> !userRoles.contains(r));
        }
    }

    @AllArgsConstructor
    private static class RolesConditionBase implements RoleCondition {

        private String role;

        @Override
        public boolean check(List<PermissionHolder> permissions) {
            return permissions.stream().anyMatch(p -> role.equals(p.getName()));
        }
    }

    private static RoleCondition buildFromString(String value) {
        RoleCondition result = null;
        if (value.startsWith(ALL_OF_PREFIX)) {
            result = new AllRolesCondition(Arrays.stream(value.substring(ALL_OF_PREFIX.length()).split("${symbol_escape}${symbol_escape},|${symbol_escape}${symbol_escape}s|;|:")).filter(s -> s != null && !s.isEmpty()).collect(Collectors.toSet()));
        } else if (value.startsWith(ANY_OF_PREFIX)) {
            result = new AnyRoleCondition(Arrays.stream(value.substring(ANY_OF_PREFIX.length()).split("${symbol_escape}${symbol_escape},|${symbol_escape}${symbol_escape}s|;|:")).filter(s -> s != null && !s.isEmpty()).collect(Collectors.toSet()));
        } else {
            result = new RolesConditionBase(value);
        }
        return result;
    }
}