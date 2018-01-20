#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import ${package}.architecture.annotations.WebApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@WebApplication
public class App implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/static/index.html");
    }

    public static void main(String[] args) {
        SpringApplication ${artifactId} = new SpringApplication(App.class);
        ${artifactId}.setBannerMode(Banner.Mode.OFF);
        ${artifactId}.run(args);
    }
}
