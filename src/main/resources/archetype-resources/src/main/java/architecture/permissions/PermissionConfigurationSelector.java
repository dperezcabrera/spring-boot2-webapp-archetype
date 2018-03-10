#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.permissions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionConfigurationSelector {

    @Bean
    public PermissionRegistryPostProcessor loggingRegistryPostProcessor() {
        return new PermissionRegistryPostProcessor();
    }
}
