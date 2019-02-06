package com.aelastic.aelasticauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

// this class is used for providing the OAuth2 server a mechanism to
// authenticate user and return the user information for the authenticating user
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // this is used by Spring security to handle authentication
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // the UserDetailsService is used by Spring to handle user information
    // that will be returned by Spring security
    // we will extend this class to use a mongo repository
    @Override
    @Bean
    @Primary
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    // we define the users, their passwords and roles
    // to be replaced with mongo repository
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("dan")
                .password("{noop}siri")
                .roles("USER")
                .and()
                .withUser("cata")
                .password("{noop}foca")
                .roles("USER", "ADMIN");
    }


}
