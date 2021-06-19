package com.app.server.security;

import com.app.server.property.RealmProperties;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakBaseSpringBootConfiguration;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class ResourceServerConfig extends KeycloakWebSecurityConfigurerAdapter {


    Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
//    Keycloak Admin Client Setup: https://gist.github.com/thomasdarimont/c4e739c5a319cf78a4cff3b87173a84b
    //set User to 'realm-admin' in realm-management client
    // backend-client needs to allow "Direct Access Grants: Resource Owner Password Credentials Grant"

    @Bean
    public Keycloak keycloak(
            KeycloakSpringBootProperties props,
            RealmProperties realmProperties
    ) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(props.getAuthServerUrl())
                .realm(props.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(props.getResource())
                .clientSecret((String) props.getCredentials().get("secret"))
                .username(realmProperties.getAdminUsername())
                .password(realmProperties.getPassword())
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return keycloak;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedOrigins(Collections.singletonList("http://[2003:c2:7735:2c00:1ce6:af26:b23c:b028]:4200"));
//        config.setAllowedOrigins(Arrays.asList("http://localhost:9090"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Activate Keycloak Security:
        //        super.configure(http);
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
//        http.cors().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy
    sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Ensures the correct registration of KeycloakSpringBootConfigResolver when Keycloaks AutoConfiguration
     * is explicitly turned off in application.yml {@code keycloak.enabled: false}.
     */
    @Configuration
    static class CustomKeycloakBaseSpringBootConfiguration extends KeycloakBaseSpringBootConfiguration {
    }
}
