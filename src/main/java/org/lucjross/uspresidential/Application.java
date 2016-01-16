package org.lucjross.uspresidential;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by lucas on 11/24/2014.
 */
@SpringBootApplication
@Import(SwaggerConfig.class)
public class Application extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebEnvironment(true);
        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class CustomSecurity extends WebSecurityConfigurerAdapter {

        @SuppressWarnings("SpringJavaAutowiringInspection")
        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().realmName("uspresidential")
                .and()
                    .authorizeRequests()
                        .antMatchers(
                                "/index.html",
                                "/home.html",
                                "/login.html",
                                "/register.html",
                                "/").permitAll()
                        .antMatchers("/public-api/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                    .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                    .addFilter(new BasicAuthenticationFilter(authenticationManager()))
                    .logout();
//                    .antMatchers("/presidents-api/**").authenticated()
//                    .and().httpBasic().realmName("presidents-api");
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }

        @Autowired
        private UserDetailsService prezUserDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(prezUserDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    protected static class CsrfHeaderFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(
                HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse,
                FilterChain filterChain) throws ServletException, IOException {

            CsrfToken csrf = (CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName());
            if (csrf != null) {
                Cookie cookie = WebUtils.getCookie(httpServletRequest, "XSRF-TOKEN");
                String token = csrf.getToken();
                if (cookie == null || !Objects.equals(token, cookie.getValue())) {
                    cookie = new Cookie("XSRF-TOKEN", token);
                    cookie.setPath(httpServletRequest.getServletContext().getContextPath());
                    httpServletResponse.addCookie(cookie);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
