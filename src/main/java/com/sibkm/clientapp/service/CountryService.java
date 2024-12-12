package com.sibkm.clientapp.service;

import com.sibkm.clientapp.entity.Country;
import com.sibkm.clientapp.helper.BasicHeaderHelper;
import com.sibkm.clientapp.model.request.CountryRequest;
import com.sibkm.clientapp.model.response.CountryResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CountryService {

  @Value("${server.base.url}/country")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  public List<Country> getAll() {
    return restTemplate
      .exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Country>>() {}
      )
      .getBody();
  }

  // todo: without dto
  public Country getById(Integer id) {
    log.info("endpoint serverapp = {}", url.concat("/" + id));

    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.GET, null, Country.class)
      .getBody();
  }

  // todo: with dto
  public CountryResponse getByIdDTO(Integer id) {
    log.info("endpoint serverapp = {}", url.concat("/" + id));

    return restTemplate
      .exchange(
        url.concat("/res/" + id),
        HttpMethod.GET,
        null,
        CountryResponse.class
      )
      .getBody();
  }

  // todo: without dto
  public Country create(Country country) {
    return restTemplate
      .exchange(
        url,
        HttpMethod.POST,
        new HttpEntity<Country>(country),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  // todo: with dto
  public Country createDTO(CountryRequest countryRequest) {
    return restTemplate
      .exchange(
        url.concat("/dto-manual"),
        HttpMethod.POST,
        new HttpEntity<CountryRequest>(countryRequest),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  public Country update(Integer id, Country country) {
    HttpEntity<Country> request = new HttpEntity<Country>(country);
    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.PUT, request, Country.class)
      .getBody();
  }

  public Country delete(Integer id) {
    return restTemplate
      .exchange(url.concat("/" + id), HttpMethod.DELETE, null, Country.class)
      .getBody();
  }
}
