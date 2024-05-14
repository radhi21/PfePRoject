package com.demo.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController 
@RequestMapping("/api/test")
public class TestController { 

  @GetMapping("/all")
  public String allAccess() { 
    return "Public Content."; 
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('RH') or hasRole('TECH')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  
  @GetMapping("/rh")
  @PreAuthorize("hasRole('RH')")
  public String rhAccess() {
    return "RH Board.";
  }
  
  @GetMapping("/tech")
  @PreAuthorize("hasRole('TECH')")
  public String techAccess() {
    return "Tech Board.";
  }
}
