package com.sibkm.clientapp.helper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BasicHeaderHelper {

  public static String createBasicToken(String username, String password) {
    String auth = username + ":" + password;
    return Base64
      .getEncoder()
      .encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
  }

  public static HttpHeaders createBasicHeaders() {
    // ? check session
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();

    if (
      auth == null ||
      auth.getPrincipal() == null ||
      auth.getCredentials() == null
    ) {
      throw new IllegalStateException("Authentication incomplete!!!");
    }

    // ? create basic token => username & password -> Authorization, Basic username:password
    String token = createBasicToken(
      // auth.getName().toString(),
      auth.getPrincipal().toString(),
      auth.getCredentials().toString()
    );

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + token);
    return headers;
  }
}
