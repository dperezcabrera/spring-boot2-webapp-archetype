#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionContextHolder {

    private static final ThreadLocal<List<PermissionHolder>[]> PERMISSION_LIST = ThreadLocal.withInitial(() -> new List[1]);

    public static <T> List<PermissionHolder<T>> getPermissions() {
        List<PermissionHolder<T>> result;
        List<PermissionHolder> temp = PERMISSION_LIST.get()[0];
        if (temp == null) {
            result = Collections.emptyList();
        } else {
            result = temp.stream().map(p -> (PermissionHolder<T>) p).collect(Collectors.toList());
        }
        return result;
    }

    public static <T> void checkPermissions(@NonNull String requiredPermission, @NonNull Predicate<PermissionHolder<T>> predicate, @NonNull Supplier<String> messageSupplier) {
        List<PermissionHolder> temp = PERMISSION_LIST.get()[0];
        if (temp == null || !temp.stream().filter(p -> p.getName().equals(requiredPermission)).map(p -> (PermissionHolder<T>) p).anyMatch(predicate)) {
            throw new DeniedPermissionException(messageSupplier.get(), requiredPermission);
        }
    }

    static void set(List<PermissionHolder> list) {
        PERMISSION_LIST.get()[0] = list;
    }

    static List<PermissionHolder> getFastPermissions() {
        return PERMISSION_LIST.get()[0];
    }
}
