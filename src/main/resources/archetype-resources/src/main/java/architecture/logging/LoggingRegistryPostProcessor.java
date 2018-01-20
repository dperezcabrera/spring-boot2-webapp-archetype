#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class LoggingRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String LOGGING_ADVISOR = LoggingAdvisor.class.getName();

    private static boolean initialized = false;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // vacio
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        if (!init()) {
            AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
            RootBeanDefinition beanDefinition = new RootBeanDefinition(LoggingAdvisor.class);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(LOGGING_ADVISOR, beanDefinition);
        }
    }

    private static synchronized boolean init() {
        boolean result = initialized;
        initialized = true;
        return result;
    }
}
