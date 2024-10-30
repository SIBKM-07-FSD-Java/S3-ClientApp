package com.sibkm.clientapp.controller;

import com.sibkm.clientapp.entity.Region;
import com.sibkm.clientapp.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/region")
public class RegionController {

  private RegionService regionService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("regions", regionService.getAll());
    return "region/index";
  }

  @GetMapping("/create")
  public String createView(Region region, Model model) {
    model.addAttribute("region", new Region());
    return "region/create-form";
  }

  @PostMapping
  public String create(Region region) {
    regionService.create(region);
    return "redirect:/region";
  }
}
