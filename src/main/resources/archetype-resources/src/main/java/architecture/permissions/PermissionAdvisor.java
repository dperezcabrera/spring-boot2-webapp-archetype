#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import ${package}.architecture.utilities.ReflectionUtil;
import java.lang.reflect.Method;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

@Slf4j
@Component
public class PermissionAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = 1L;

    private PermissionAware<?> permissionAware;

    private static Advice advice;
    
    public PermissionAdvisor(@Lazy @Autowired PermissionAware<?> permissionAware) {
        super.setOrder(HIGHEST_PRECEDENCE);
        this.permissionAware = permissionAware;
    }

    private static final Pointcut POINTCUT = new StaticMethodMatcherPointcut() {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return ReflectionUtil.getAnnotation(method, targetClass, RequiredPermission.class).isPresent();
        }
    };

    @Override
    public Pointcut getPointcut() {
        return POINTCUT;
    }

    @Override
    public Advice getAdvice() {
        return getAdvice(permissionAware);
    }
    
    private static synchronized Advice getAdvice(PermissionAware<?> permissionAware){
        if (advice == null){
            advice = new PermissionMethodValidationInterceptor(permissionAware);
        }
        return advice;
    }
}
