package com.app.server.security;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.spring.boot.oauth.Okta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Value("${app.okta.apiToken}")
    private String apiToken;

    @Value("${app.okta.orgUrl}")
    private String orgUrl;


    @Bean
    public Client getClient() {
        Client client = Clients.builder()
                .setOrgUrl(orgUrl)
                .setClientCredentials(new TokenClientCredentials(apiToken))
                .build();
        return client;
    }

//    @Bean
//    WebClient webClient(ClientRegistrationRepository clientRegistrations,
//                        OAuth2AuthorizedClientRepository authorizedClients) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
//                clientRegistrations, authorizedClients);
//        oauth2.setDefaultOAuth2AuthorizedClient(true);
//        oauth2.setDefaultClientRegistrationId("okta");
//        return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
//    }




//    @Autowired
//    private JwtAuthEntryPoint unauthorizedHandler;
//



//    @Bean
//    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
//        return new JwtAuthTokenFilter();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


//         http.cors().and().csrf().disable();


//                  authorizeRequests().
//                  	antMatchers("/api/**").permitAll();

        http.
//        	cors().and().csrf().disable().
        authorizeRequests().
//            .antMatchers("/api/samplepool/public").permitAll()
        antMatchers("/api/samplepool/downloadFile/**").permitAll()
                .anyRequest().authenticated()
//            .and().oauth2Client()
//           .and().oauth2Login()
                .and()
                .oauth2ResourceServer().jwt();

        http.cors();

        Okta.configureResourceServer401ResponseBody(http);
//
    }
}


