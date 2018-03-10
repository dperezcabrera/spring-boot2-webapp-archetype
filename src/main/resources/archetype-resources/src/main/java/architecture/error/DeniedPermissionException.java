#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.error;

import lombok.Getter;

public class DeniedPermissionException extends RuntimeException {
    
    @Getter
    private final String requiredPermission;
    
    public DeniedPermissionException(String message, String requiredPermission) {
        super(message);
        this.requiredPermission = requiredPermission;
    }
}
