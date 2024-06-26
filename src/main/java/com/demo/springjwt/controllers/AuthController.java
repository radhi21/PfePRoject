package com.demo.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.request.SignupRequest;
import com.demo.springjwt.payload.response.JwtResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.repository.RoleRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import com.demo.springjwt.security.services.UserDetailsImpl;
 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository; 

  @Autowired
  RoleRepository roleRepository;
 
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils; 
  
  

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
	  System.out.println(signUpRequest.toString());
	  if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "rh":
          Role rhRole = roleRepository.findByName(ERole.ROLE_RH)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(rhRole);

          break;
        case "tech":
            Role techRole = roleRepository.findByName(ERole.ROLE_TECH)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(techRole);

            break;
          
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    
    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  } 
    
 
 
    

  @GetMapping("/users/role/user")
  public ResponseEntity<List<User>> getUsersByRoleUser() {
      List<User> usersWithUserRole = userRepository.findAllByRolesName(ERole.ROLE_USER);
      return ResponseEntity.ok(usersWithUserRole);
  }
  

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
      Optional<User> userData = userRepository.findById(id);
      return userData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
      Optional<User> userData = userRepository.findById(id);
      if (userData.isPresent()) {
          User user = userData.get();
          user.setUsername(userDetails.getUsername());
          user.setEmail(userDetails.getEmail());
          user.setPassword(encoder.encode(userDetails.getPassword()));
          userRepository.save(user);
          return ResponseEntity.ok(user);
      } else {
          return ResponseEntity.notFound().build(); 
      } 
  }    
 
  @DeleteMapping("/users/{id}")
  public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
          userRepository.deleteById(id);
          return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
      } else {
          return ResponseEntity.notFound().build();
      }
  }
  }