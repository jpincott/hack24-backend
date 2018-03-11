package com.experian.hack.backend;

import esendex.sdk.java.ServiceFactory;
import esendex.sdk.java.service.BasicServiceFactory;
import esendex.sdk.java.service.auth.UserPassword;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsendexConfiguration {
    @Bean
    public BasicServiceFactory serviceFactory(
            @Value("esendex.username") String username,
            @Value("esendex.password") String password) {
        return ServiceFactory.createBasicAuthenticatingFactory(new UserPassword(username, password));
    }
}
