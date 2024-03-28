package com.application.ptsdwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
    public String startPage(Model model) {
    return "main/start-page";
  }

  @GetMapping("/login")
    public String loginPage() {
    return "main/login";
  }

  @GetMapping("/yourpage")
  public String userStartPage() {
    return "user/user-start-page";
  }

  @GetMapping("/adminpage")
  public String adminStartPage(Model model) {
    return "admin/admin-start-page";
  }
    
}
