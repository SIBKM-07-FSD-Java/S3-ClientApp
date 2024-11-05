package com.sibkm.clientapp.controller;

import com.sibkm.clientapp.entity.Country;
import com.sibkm.clientapp.model.request.CountryRequest;
import com.sibkm.clientapp.service.CountryService;
import com.sibkm.clientapp.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/country")
public class CounrtyController {

  private CountryService countryService;
  private RegionService regionService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("countries", countryService.getAll());
    model.addAttribute("isActive", "country");
    return "country/index";
  }

  // todo: without dto
  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model) {
    model.addAttribute("isActive", "country");
    model.addAttribute("country", countryService.getById(id));
    return "country/detail";
  }

  // todo: with dto
  @GetMapping("/res/{id}")
  public String getByIdDTO(@PathVariable Integer id, Model model) {
    model.addAttribute("isActive", "country");
    model.addAttribute("country", countryService.getByIdDTO(id));
    return "country/detail";
  }

  // todo: without dto
  @GetMapping("/create")
  public String createView(Country country, Model model) {
    model.addAttribute("country", new Country());
    model.addAttribute("regions", regionService.getAll());
    model.addAttribute("isActive", "country");
    return "country/create-form";
  }

  @PostMapping
  public String create(Country country) {
    countryService.create(country);
    return "redirect:/country";
  }

  // todo: with dto
  @GetMapping("/create-dto")
  public String createViewDTO(CountryRequest countryRequest, Model model) {
    model.addAttribute("country", new CountryRequest());
    model.addAttribute("regions", regionService.getAll());
    model.addAttribute("isActive", "country");
    return "country/create-form_dto";
  }

  @PostMapping("/dto")
  public String createDTO(CountryRequest countryRequest) {
    countryService.createDTO(countryRequest);
    return "redirect:/country";
  }

  @GetMapping("/update/{id}")
  public String updateView(
    @PathVariable Integer id,
    Country country,
    Model model
  ) {
    model.addAttribute("country", countryService.getById(id));
    model.addAttribute("regions", regionService.getAll());
    model.addAttribute("isActive", "country");
    return "country/update-form";
  }

  @PutMapping("/{id}")
  public String update(@PathVariable Integer id, Country country) {
    countryService.update(id, country);
    return "redirect:/country";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable Integer id) {
    countryService.delete(id);
    return "redirect:/country";
  }
}
