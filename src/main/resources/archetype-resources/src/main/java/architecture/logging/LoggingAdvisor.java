#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import ${package}.architecture.common.ReflectionUtil;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = 1L;

    private Advice advice = new LoggingMethodValidationInterceptor();
    private Pointcut pointcut;

    private final ApplicationContext applicationContext;

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

    private synchronized Pointcut getPointcutOrBuild(List<String> defaultPackages) {
        if (pointcut == null) {
            pointcut = new LoggingMethodMatcherPointcut(defaultPackages);
        }
        return pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return getPointcutOrBuild(AutoConfigurationPackages.get(applicationContext));
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }   
}
