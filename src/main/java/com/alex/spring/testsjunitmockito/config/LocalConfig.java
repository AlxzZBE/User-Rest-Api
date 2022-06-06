package com.alex.spring.testsjunitmockito.config;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
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
                .email("alexsander_lima99@hotmail.com")
                .password("12345")
                .build());
        userRepository.save(User.builder()
                .name("Be")
                .email("testes@gmail.com")
                .password("01234")
                .build());

    }

}
