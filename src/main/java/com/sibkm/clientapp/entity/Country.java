package com.sibkm.clientapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

  private Integer id;
  private String code;
  private String name;
  private Region region;
}
