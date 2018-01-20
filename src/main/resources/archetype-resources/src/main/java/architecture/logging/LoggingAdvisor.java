#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import ${package}.architecture.utilities.ReflectionUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LoggingAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = 1L;

    private static final Advice ADVICE = new LoggingMethodValidationInterceptor();
    private static Pointcut POINTCUT;

    private ApplicationContext applicationContext;
        
    @AllArgsConstructor
    private static class LoggingMethodMatcherPointcut extends StaticMethodMatcherPointcut {

        private List<String> defaultPackages;
        private List<Class<?>> excludes;
        
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = false;
            AnnotationAttributes attributes = LoggingConfigurationSelector.getAutoLoggingConfig();
            if (attributes != null) {
                Optional<Logging> opt = ReflectionUtil.getAnnotation(method, targetClass, Logging.class);
                if (opt.isPresent()) {
                    result = !opt.get().disabled();
                } else if (excludes.contains(targetClass)) {
                    result = false;
                } else if (method.getDeclaringClass().getPackage() != null) {
                    result = interfaceMatches(method);
                }
            }
            return result;
        }

        private boolean interfaceMatches(Method method) {
            String candidatePackageName = method.getDeclaringClass().getPackage().getName();
            if (candidatePackageName != null) {
                for (String packageBase : defaultPackages) {
                    if (candidatePackageName.startsWith(packageBase + ".") || candidatePackageName.equals(packageBase)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static synchronized Pointcut getPointcutOrBuild(List<String> defaultPackages) {
        if (POINTCUT == null){
            AnnotationAttributes attributes = LoggingConfigurationSelector.getAutoLoggingConfig();
            List<Class<?>> excludes = Arrays.asList(attributes.getClassArray("excludes"));
            List<String> basePackages = Arrays.asList(attributes.getStringArray("basePackages"));
            if (basePackages.isEmpty()){
                basePackages = defaultPackages;
            }
            POINTCUT = new LoggingMethodMatcherPointcut(basePackages, excludes);
        }
        return POINTCUT;
    }
    
    @Override
    public Pointcut getPointcut() {
        return getPointcutOrBuild(AutoConfigurationPackages.get(applicationContext));
    }

    @Override
    public Advice getAdvice() {
        return ADVICE;
    }
}