package com.scaler.amit.project_userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class ProjectUserserviceApplicationTests {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Test
    void contextLoads() {
    }

    //@Test
    void addRegisterSamplePostmanClient()
    {
                RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("scaler")
                .clientSecret("$2a$12$qI9/zPEx2tITCJwGI6ni7u.9VEUxcerU2xi3YU7vsACcwomy4A9JK") //scaler-secret
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("ADMIN")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(oidcClient);
    }
}
