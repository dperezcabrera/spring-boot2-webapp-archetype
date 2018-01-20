#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.annotations;

import ${package}.architecture.logging.EnableAutoLogging;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableCaching
@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
@EnableAutoLogging
@ComponentScan
public @interface WebApplication {

    @AliasFor(annotation = EnableAutoLogging.class, attribute = "basePackages")
    String[] basePackages() default {};
}

