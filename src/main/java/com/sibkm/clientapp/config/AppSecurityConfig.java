package com.sibkm.clientapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/css/**", "/js/**", "/images/**")
          .permitAll()
          .requestMatchers("/login")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .formLogin(login ->
        login
          .loginPage("/auth/login")
          .defaultSuccessUrl("/", true)
          .failureUrl("/login?error=true")
          .permitAll()
      )
      .logout(logout ->
        logout
          .logoutUrl("/logout")
          .logoutSuccessUrl("/login?logout=true")
          .permitAll()
      );

    return http.build();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }
}
