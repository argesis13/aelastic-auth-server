package com.aelastic.aelasticauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer // we tell Spring that this service is going to act as an OAuth2 Service
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired // should be used by password grant type
    private AuthenticationManager authenticationManager;

    @Autowired // we will configure our own user details and extend this class
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Override // define which clients and how they going to be registered to our service
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("aelastic-web-ui")
                .secret("{noop}")
                .authorizedGrantTypes("password", "refresh_token", "client_credentiails")
                .scopes("webclient")
                .and()
                .withClient("aelastic-mobile-app")
                .secret("{noop}")
                .authorizedGrantTypes("password", "refresh_token", "client_credentiails")
                .scopes("mobileclient");
    }

    @Override // define which authentication manager and user details service should be used
    // meaning the authorization and the token endpoints and the token services
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(
                        jwtTokenEnhancer,
                        jwtAccessTokenConverter
                )
        );
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

//    @Override // defines the security constraints on the token endpoint
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.sslOnly().tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//    }
}
