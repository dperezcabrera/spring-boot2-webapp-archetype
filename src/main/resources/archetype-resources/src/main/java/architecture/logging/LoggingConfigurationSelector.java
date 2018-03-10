#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import java.util.Map;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class LoggingConfigurationSelector implements ImportBeanDefinitionRegistrar {

    private static AnnotationAttributes autoLoggingConfig;

    @Bean
    public LoggingRegistryPostProcessor loggingRegistryPostProcessor() {
        return new LoggingRegistryPostProcessor();
    }

    public static AnnotationAttributes getAutoLoggingConfig() {
        return autoLoggingConfig;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        initAutoLoginConfig(annotationMetadata);
    }

    private static synchronized void initAutoLoginConfig(AnnotationMetadata annotationMetadata) {
        Map<String, Object> attributesMap = annotationMetadata.getAnnotationAttributes(EnableAutoLogging.class.getName());
        if (autoLoggingConfig == null && attributesMap != null) {
            autoLoggingConfig = new AnnotationAttributes(attributesMap);          
        }
    }
}
