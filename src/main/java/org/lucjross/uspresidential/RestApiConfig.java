package org.lucjross.uspresidential;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lucas on 5/31/2015.
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestApiConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        try {
            config.setBaseUri(new URI("/api"));
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
