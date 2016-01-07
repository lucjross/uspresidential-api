package org.lucjross.uspresidential;

/**
 * Created by lucas on 5/31/2015.
 */
//@Configuration
public class RestApiConfig { //extends RepositoryRestMvcConfiguration {

    public static final String BASE_URI = "/presidents-api";

//    @Override
//    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        super.configureRepositoryRestConfiguration(config);
//        try {
//            config.setBaseUri(new URI(BASE_URI));
//        }
//        catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

//    @Bean
//    public ControllerClassNameHandlerMapping getControllerClassNameHandlerMapping() {
//        ControllerClassNameHandlerMapping c = new ControllerClassNameHandlerMapping();
//        c.setPathPrefix(BASE_URI);
//        return c;
//    }
}
