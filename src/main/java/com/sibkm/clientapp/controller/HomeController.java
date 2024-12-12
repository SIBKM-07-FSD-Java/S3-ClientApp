package com.sibkm.clientapp.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping
  public String index(Model model, Principal principal) {
    // model.addAttribute("name", "SIBKM");
    model.addAttribute("isActive", "home");
    principal.getName();
    return "index";
  }
}
