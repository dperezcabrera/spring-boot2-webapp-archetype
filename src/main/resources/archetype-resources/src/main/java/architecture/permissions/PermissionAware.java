#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import java.util.List;

public interface PermissionAware<T> {

    public List<PermissionHolder<T>> getCurrentPermissions();
}
