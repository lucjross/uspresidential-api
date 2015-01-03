package org.lucjross.uspresidential;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by lucas on 12/31/2014.
 *
 * Removed {@code @Configuration} due to issue
 * https://github.com/martypitt/swagger-springmvc/issues/462
 */
@EnableWebMvc
@EnableSwagger
@ComponentScan
public class SwaggerConfig {

}
