package com.sibkm.clientapp.service;

import com.sibkm.clientapp.model.request.LoginRequest;
import com.sibkm.clientapp.model.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthService {

  @Value("${server.base.url}/auth/login")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private SecurityContextRepository securityContextRepository;

  public Boolean login(
    LoginRequest loginRequest,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    try {
      // ? 1. consume endpoint dari be
      ResponseEntity<LoginResponse> res = restTemplate.exchange(
        url,
        HttpMethod.POST,
        new HttpEntity<LoginRequest>(loginRequest),
        LoginResponse.class
      );

      // ? check condition for login
      if (res.getStatusCode() == HttpStatus.OK) {
        // ? set principle
        setPrinciple(res.getBody(), loginRequest.getPassword());

        // ? save principle
        saveSecurityContext(request, response);

        // ? check principle
        Authentication auth = SecurityContextHolder
          .getContext()
          .getAuthentication();

        log.info("Name: {}", auth.getName());
        return true;
      } else {
        log.info("Login failed: {}", res.getStatusCode());
      }
    } catch (Exception e) {
      log.warn("Error message: {}", e.getMessage());
      return false;
    }
    return false;
  }

  public void setPrinciple(LoginResponse response, String password) {
    // ? get principle
    List<SimpleGrantedAuthority> authorities = response
      .getAuthorities()
      .stream()
      .map(authority -> new SimpleGrantedAuthority(authority))
      .toList();

    // ? create authentication token
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
      response.getUsername(),
      password,
      authorities
    );

    // ? set principle
    SecurityContextHolder.getContext().setAuthentication(token);

    log.info(
      "username: {}, password: {}, authorities: {}",
      response.getUsername(),
      password,
      authorities
    );
  }

  private void saveSecurityContext(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    // ? inisialisasi SecurityContext
    SecurityContext context = SecurityContextHolder.getContext();

    // ? save context
    securityContextRepository.saveContext(context, request, response);

    log.info("Security context saved successfully in the session...");
  }
}
