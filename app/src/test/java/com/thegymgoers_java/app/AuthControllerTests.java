package com.thegymgoers_java.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thegymgoers_java.app.model.User;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    private Optional<User> mockuser ;

    private User user;
    private User user2;
    private NewUserRequest newUserRequest = new NewUserRequest();

    @BeforeEach
    void setUp() {

        user = new User("testuser", "pass@email.com", "pass");
        user2 = new User("testname", "testemail2@dom.com", "fakepass");
        newUserRequest.setUsername("testuser");
        newUserRequest.setPassword("pass");
        newUserRequest.setEmailAddress("pass@email.com");
//        mockuser = new Optional<User>("mock", "email.com", "pass");
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
        void shouldReturn400IfUsernameDoesNotMatch() throws Exception {

            when(userService.login(any(User.class)))
                    .thenThrow(new IllegalArgumentException("Incorrect Username: " + user.getUsername()));

            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Incorrect Username: " + user.getUsername()))
                    .andDo(print());
        }

        @Test
        void shouldReturn400IfEmailAddressDoesNotMatch() throws Exception {

            when(userService.login(any(User.class)))
                    .thenThrow(new IllegalArgumentException("Incorrect Email: " + user.getEmailAddress()));

            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Incorrect Email: " + user.getEmailAddress()))
                    .andDo(print());
        }

        @Test
        void shouldReturn200IfLoginSuccessful() throws Exception {

            when(userService.login(any(User.class)))
                    .thenReturn(user);

            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        void shouldReturn400IfUsernameIsEmpty() throws Exception {
            User newUser = new User("", "testemail@email.com", "fakepass");

            // Mocking the userService's response for when a user's login details are empty
            when(userService.login(newUser))
                    .thenThrow(new IllegalArgumentException("User details cannot not be empty or null"));


            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUser)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Invalid request content."))
                    .andDo(print());
        }

        @Test
        void shouldReturn400IfUsernameIsNull() throws Exception {
            User newUser = new User(null, "testemail@email.com", "fakepass");

            // Mocking the userService's response for when a user's login details are empty
            when(userService.login(newUser))
                    .thenThrow(new IllegalArgumentException("User details cannot not be empty or null"));


            // Asserting a unsuccessful 400 response
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newUser)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Invalid request content."))
                    .andDo(print());
        }
    }
    }


