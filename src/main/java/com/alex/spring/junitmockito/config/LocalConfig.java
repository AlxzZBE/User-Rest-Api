package com.alex.spring.junitmockito.config;

import com.alex.spring.junitmockito.domain.User;
import com.alex.spring.junitmockito.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("local")
public class LocalConfig {

    private final UserRepository userRepository;

    @Bean
    public void startDb() {
        userRepository.save(User.builder()
                .name("Alex")
                .email("alex_@mail.com")
                .password("12345")
                .build());
        userRepository.save(User.builder()
                .name("Be")
                .email("testes@gmail.com")
                .password("01234")
                .build());

    }
}