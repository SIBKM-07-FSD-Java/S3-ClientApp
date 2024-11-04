package com.sibkm.clientapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {

  private Integer countryId;
  private String countryCode;
  private String countryName;
  private Integer regionId;
  private String regionName;
}
