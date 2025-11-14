package com.scaler.amit.project_userservice.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Configuration
@Profile("docker") // Run this only when the "docker" profile is active
public class OAuthClientRegistrationRunner {

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth.client.redirectUri}")
    private String redirectUri;

    @Value("${oauth.client.postLogoutRedirectUri}")
    private String postLogoutRedirectUri;

    //This command line runner is to register New OAuth client as per details provided in application-docker.properties
    //This will  as soon as application start before running any query
    @Bean
    public CommandLineRunner registerOAuthClient(RegisteredClientRepository registeredClientRepository, BCryptPasswordEncoder getBCryptPasswordEncoder) {
        return args -> {
            RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(clientId)
                    .clientSecret(getBCryptPasswordEncoder.encode(clientSecret))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri(redirectUri)
                    .postLogoutRedirectUri(postLogoutRedirectUri)
                    .scope("ADMIN")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();
            registeredClientRepository.save(oidcClient);
            System.out.println("OAuth client registered successfully in Docker!");
        };
    }
}
