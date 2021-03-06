package org.lucjross.uspresidential;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lucas on 1/8/2015.
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(WEB_JAR_RESOURCE_PATTERNS)
//                .addResourceLocations(WEB_JAR_RESOURCE_LOCATION)
//                .setCachePeriod(0);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
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
