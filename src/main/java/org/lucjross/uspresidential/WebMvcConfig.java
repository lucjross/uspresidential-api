package org.lucjross.uspresidential;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.*;

/**
 * Created by lucas on 1/8/2015.
 */
//@Configuration
//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(WEB_JAR_RESOURCE_PATTERNS)
//                .addResourceLocations(WEB_JAR_RESOURCE_LOCATION)
//                .setCachePeriod(0);
//    }
//
//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix(WEB_JAR_VIEW_RESOLVER_PREFIX);
//        resolver.setSuffix(WEB_JAR_VIEW_RESOLVER_SUFFIX);
//        return resolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
}
