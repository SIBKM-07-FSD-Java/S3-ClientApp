package com.sibkm.clientapp.service;

import com.sibkm.clientapp.entity.Region;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RegionService {

  @Value("${server.base.url}/region")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  public List<Region> getAll() {
    // ? format => "Authorization", "Basic username:password"
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic amFrYTpqYWth");
    return restTemplate
      .exchange(
        url,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<List<Region>>() {}
      )
      .getBody();
  }

  public Region getById(Integer id) {
    log.info("endpoint serverapp = {}", url.concat("/" + id));

    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.GET, null, Region.class)
      .getBody();
  }

  public Region create(Region region) {
    return restTemplate
      .exchange(
        url,
        HttpMethod.POST,
        new HttpEntity<Region>(region),
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }

  public Region update(Integer id, Region region) {
    HttpEntity<Region> request = new HttpEntity<Region>(region);
    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.PUT, request, Region.class)
      .getBody();
  }

  public Region delete(Integer id) {
    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.DELETE, null, Region.class)
      .getBody();
  }
}
