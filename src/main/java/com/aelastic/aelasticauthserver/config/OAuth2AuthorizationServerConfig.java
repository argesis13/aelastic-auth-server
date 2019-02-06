package com.aelastic.aelasticauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer // we tell Spring that this service is going to act as an OAuth2 Service
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired // should be used by password grant type
    AuthenticationManager authenticationManager;

    @Autowired // we will configure our own user details and extend this class
    UserDetailsService userDetailsService;

    @Override // define which clients and how they going to be registered to our service
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("aelastic-web-ui")
                .authorizedGrantTypes("password", "refresh_token", "client_credentiails")
                .scopes("webclient")
                .and()
                .withClient("aelastic-mobile-app")
                .authorizedGrantTypes("password", "refresh_token", "client_credentiails")
                .scopes("mobileclient");
    }

    @Override // define which authentication manager and user details service should be used
    // meaning the authorization and the token endpoints and the token services
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());

    }

    @Bean
    // we use self signed certificate to encode/decode the token
    // translates between encoded token values and OAuth authentication information in both directions
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "siri0104".toCharArray()).getKeyPair("selfsigned");
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Override // defines the security constraints on the token endpoint
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.sslOnly().tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }



}
