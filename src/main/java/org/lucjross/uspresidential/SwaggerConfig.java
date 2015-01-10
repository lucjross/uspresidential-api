package org.lucjross.uspresidential;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.*;


/**
 * Created by lucas on 12/31/2014.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "uspresidential-api",
                "API for U.S. Presidential",
                "[terms of service URL]",
                "lucjross@gmail.com",
                "[license type]",
                "[license URL]");
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(WEB_JAR_RESOURCE_PATTERNS)
//                .addResourceLocations(WEB_JAR_RESOURCE_LOCATION)
//                .setCachePeriod(0);
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        System.out.println("configuring view resolvers");
////        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////        resolver.setPrefix(WEB_JAR_VIEW_RESOLVER_PREFIX);
////        resolver.setSuffix(WEB_JAR_VIEW_RESOLVER_SUFFIX);
////        registry.viewResolver(resolver);
//        registry.jsp(WEB_JAR_VIEW_RESOLVER_PREFIX, WEB_JAR_VIEW_RESOLVER_SUFFIX);
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
