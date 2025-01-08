package com.thegymgoers_java.app.controller;

import com.thegymgoers_java.app.configuration.jwt.JwtUtils;
import com.thegymgoers_java.app.configuration.services.UserDetailsImpl;
import com.thegymgoers_java.app.model.ERole;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.payload.request.LoginRequest;
import com.thegymgoers_java.app.payload.request.NewUserRequest;
import com.thegymgoers_java.app.payload.response.JwtResponse;
import com.thegymgoers_java.app.payload.response.MessageResponse;
import com.thegymgoers_java.app.repository.UserRepository;
import com.thegymgoers_java.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    AuthenticationManager authenticationManager;

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        if (userRepository.findByUsername(newUserRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.findByEmailAddress(newUserRequest.getEmailAddress()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(newUserRequest.getUsername(),
                newUserRequest.getEmailAddress(),
                encoder.encode(newUserRequest.getPassword()));

        Set<String> strRoles = newUserRequest.getRole();
        Set<ERole> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(ERole.USER);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    roles.add(ERole.ADMIN);
                } else {
                    roles.add(ERole.USER);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@Valid @RequestBody NewUserRequest newUserRequest){
//        try {
//            // attempts to register the new user
//            User registeredUser = userService.register(new User(newUserRequest.getUsername(),
//                    newUserRequest.getEmailAddress(), newUserRequest.getPassword()));
//            // returns null if a user already exists with the email/username used to register
//            if(registeredUser == null){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that email/username already exists");
//            }
//        }catch (Exception e) {
//            // Exception thrown when the user's email/username is either empty or null
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter A Valid User details");
//        }
//        return ResponseEntity.ok("User Reg Successful");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@Valid @RequestBody User userToLogin){
//        try{
//            // Attempts to login with a given user's details
//            User user = userService.login(userToLogin);
//
//            // If a user is returned, the login was successful
//            if(!(user == null)) {
//                return new ResponseEntity<>(user, HttpStatus.OK);
//            }
//        }catch (Exception e){
//            // Displays an error based on if the details are invalid
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//
//        }
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    }
}
