package org.lucjross.uspresidential;

import org.ajar.swaggermvcui.SwaggerSpringMvcUi;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import javax.sql.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by lucas on 11/24/2014.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@Import(value=SwaggerConfig.class)
public class Application extends SpringBootServletInitializer {

    static final ApplicationListener<ApplicationEnvironmentPreparedEvent> appEnvPreparedListener =
            new ApplicationListener<ApplicationEnvironmentPreparedEvent>() {

                @Override
                public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
                    event.getSpringApplication().setSources(Collections.singleton(SwaggerConfig.class));
                }
            };

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        builder.application().addListeners(appEnvPreparedListener);
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebEnvironment(true);
//        app.addListeners(appEnvPreparedListener);
        app.run(args);
    }

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() throws ClassNotFoundException {
        Class.forName(env.getRequiredProperty("spring.datasource.driverClassName"));

        String uri = env.getRequiredProperty("spring.datasource.url");
        String user = env.getRequiredProperty("spring.datasource.username");
        String pass = env.getRequiredProperty("spring.datasource.password");
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(uri, user, pass);
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource =
                new PoolingDataSource<>(connectionPool);
        return dataSource;
    }
}
