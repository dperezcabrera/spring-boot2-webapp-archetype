#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class PermissionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String PERMISSION_ADVISOR = PermissionAdvisor.class.getName();

    private static boolean initialized = false;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // empty
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        if (!initialized()) {
            AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
            RootBeanDefinition beanDefinition = new RootBeanDefinition(PermissionAdvisor.class);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(PERMISSION_ADVISOR, beanDefinition);
        }
    }

    private static synchronized boolean initialized() {
        boolean result = initialized;
        initialized = true;
        return result;
    }
}
