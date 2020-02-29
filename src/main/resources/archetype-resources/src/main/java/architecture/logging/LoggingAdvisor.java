#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import ${package}.architecture.common.ReflectionUtil;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LoggingAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = 1L;

    private final Advice advice = new LoggingMethodValidationInterceptor();
    
    private final Pointcut pointcut;

    public LoggingAdvisor(ApplicationContext applicationContext) {
        this.pointcut = new LoggingMethodMatcherPointcut(AutoConfigurationPackages.get(applicationContext));
    }

    @RequiredArgsConstructor
    private static class LoggingMethodMatcherPointcut extends StaticMethodMatcherPointcut {

        private final List<String> defaultPackages;

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = false;
            Optional<Logging> opt = ReflectionUtil.getAnnotation(method, targetClass, Logging.class);
            if (opt.isPresent()) {
                result = !opt.get().disabled();
            } else if (method.getDeclaringClass().getPackage() != null) {
                result = interfaceMatches(method);
            }
            return result;
        }

        private boolean interfaceMatches(Method method) {
            String candidatePackageName = method.getDeclaringClass().getPackage().getName();
            if (candidatePackageName != null) {
                return defaultPackages.stream().anyMatch(p -> (candidatePackageName.startsWith(p + ".") || candidatePackageName.equals(p)));
            }
            return false;
        }
    }   
}
