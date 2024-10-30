package com.sibkm.clientapp.service;

import com.sibkm.clientapp.entity.Region;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RegionService {

  private RestTemplate restTemplate;

  public List<Region> getAll() {
    return restTemplate
      .exchange(
        "http://localhost:9000/region",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Region>>() {}
      )
      .getBody();
  }

  public Region create(Region region) {
    return restTemplate
      .exchange(
        "http://localhost:9000/region",
        HttpMethod.POST,
        new HttpEntity<Region>(region),
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }
}
