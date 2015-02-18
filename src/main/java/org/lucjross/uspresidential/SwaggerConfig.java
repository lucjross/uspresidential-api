package org.lucjross.uspresidential;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Objects;


/**
 * Created by lucas on 12/31/2014.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig).apiInfo(
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

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
