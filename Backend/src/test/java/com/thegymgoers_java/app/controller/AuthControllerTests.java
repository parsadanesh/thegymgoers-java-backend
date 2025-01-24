package com.thegymgoers_java.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thegymgoers_java.app.configuration.jwt.JwtUtils;
import com.thegymgoers_java.app.configuration.services.UserDetailsImpl;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.payload.request.LoginRequest;
import com.thegymgoers_java.app.payload.request.NewUserRequest;
import com.thegymgoers_java.app.repository.UserRepository;
import com.thegymgoers_java.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private AuthController authController;

    @MockBean
    UserRepository userRepository;

    private Optional<User> mockuser ;

    private User user;
    private User user2;
    private NewUserRequest newUserRequest = new NewUserRequest();
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {

        user = new User("testuser", "pass@email.com", "pass");
        user2 = new User("testname", "testemail2@dom.com", "fakepass");
        newUserRequest.setUsername("testuser");
        newUserRequest.setPassword("pass");
        newUserRequest.setEmailAddress("pass@email.com");
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        loginRequest = new LoginRequest();
        loginRequest.setUsername("pass");
        loginRequest.setPassword("wrongpass");
    }

    @Nested
    class RegisterTests{

        @Test
        void createUserHttpRequest() throws Exception {
            // Mocking a successful response from the database
            when(userRepository.save(user)).thenReturn(user);

            // Mocking a call to the api with valid registration details
            // Asserting a successful 200 response
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("User registered successfully!"))
                    .andDo(print());
        }

        @Test
        void createUserWithSameUsername() throws Exception {
            // Mocking a user with the same email/username response
            when(userRepository.findByUsername(newUserRequest.getUsername())).thenReturn(Optional.of(user));

            // Mocking a call to the api with registration with the same username
            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUserRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Error: Username is already taken!"))
                    .andDo(print());
        }

        @Test
        void createUserWithSameEmail() throws Exception {
            // Mocking a user with the same email/username response
            when(userRepository.findByEmailAddress(newUserRequest.getEmailAddress())).thenReturn(Optional.of(user));

            // Mocking a call to the api with registration with the same username
            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUserRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Error: Email is already in use!"))
                    .andDo(print());
        }

        @Test
        void createUserWithNullUsername() throws Exception {
            // Mocking a user with the same email/username response
            newUserRequest.setUsername(null);
//            when(userRepository.findByUsername(newUserRequest.getUsername())).thenReturn(Optional.of(user));

//            when(userRepository.save(user)).thenReturn(user);

            // Mocking a call to the api with registration with the same username
            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUserRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Invalid request content."))
//                    .andExpect(jsonPath("$.message").value("Error: Username is already taken!"))
                    .andDo(print());
        }

        @Test
        void creatingNullUser() throws Exception {

            User newUser = new User("testuser", null, "fakepass");

            // Mocking the userService response
//            when(userService.register(newUser))
//                    .thenThrow(new IllegalArgumentException("User details cannot not be empty or null"));

            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUser)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Invalid request content."))
                    .andDo(print());
        }
    }

    @Nested
    class LoginTests {


        @Test
        void shouldReturn401IfUsernameDoesNotMatch() throws Exception {
            // Mocking a user with the same email/username response
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("invalidusername");
            loginRequest.setPassword("pass");

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new RuntimeException("Bad credentials"));

            mockMvc.perform(post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());

        }

        @Test
        void shouldReturn200IfUsernamePasswordMatch() throws Exception {
            mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("validusername");
            loginRequest.setPassword("validpassword");

            Authentication authentication = mock(Authentication.class);
            UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(jwtUtils.generateJwtToken(authentication)).thenReturn("valid-jwt-token");

            mockMvc.perform(post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        void checkWhenPasswordDontMatch() throws Exception {

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Bad credentials"));

            // Asserting an unsuccessful 401 response
            mockMvc.perform(post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string("Error: Incorrect password"))
                    .andDo(print());
        }


        @Test
        void shouldReturn400IfUsernameIsEmpty() throws Exception {
            loginRequest.setUsername(null);

            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Invalid request content."))
                    .andDo(print());
        }

    }
    }


