package com.thegymgoers_java.app;

import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.model.Workout;
import com.thegymgoers_java.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        user = new User("testname", "testemail@dom.com", "fakepass");
        user2 = new User("testname2", "testemail2@dom.com", "fakepass");
    }

    @Test
    void shouldReturnEmptyArray() throws Exception {
        List<Workout> workoutList = new ArrayList<>();
        when(userService.getWorkouts(user.getUsername()))
                .thenReturn(workoutList);

        mockMvc.perform(get("/users/{username}/workouts", user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"))
                        .andDo(print());
    }

    @Test
    @WithMockUser(username = "testname", roles = {"USER"})
    void shouldReturn200WhenAddingAWorkout() throws Exception {
        // Mocking the json request body
        String json = "{ \"exercises\": [ { \"exerciseName\": \"Push-up\", \"reps\": 15, \"sets\": 3, \"weight\": 20 }, { \"exerciseName\": \"Squat\", \"reps\": 20, \"sets\": 3, \"weight\": 30 } ], \"dateCreated\": \"\" }";

        Workout workout = objectMapper.readValue(json, Workout.class);
        user.addWorkout(workout);

        when(userService.addWorkout(any(String.class), any(Workout.class))).thenReturn(user);

        mockMvc.perform(post("/users/{username}/workouts", user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workoutsList[0].exercises[0].exerciseName").value("Push-up"))
                .andExpect(jsonPath("$.workoutsList[0].exercises[1].exerciseName").value("Squat"))
                .andDo(print());

        verify(userService).addWorkout(argThat(username -> username.equals(user.getUsername())), any(Workout.class));
    }
//
//    @Test
//    void createUserWithSameUsername() throws Exception {
//
//
//        when(userService.register(user2)).thenReturn(null);
//
//
//        mockMvc.perform(post("/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user2)))
//                        .andExpect(status().isBadRequest())
//                        .andExpect(content().string("User Reg failed"))
//                        .andDo(print());
//
//
////        verify(userService)
//    }
//
//    @Test
//    void creatingNullUser() throws Exception {
//
//        User newUser = new User("testuser", null, "fakepass");
//
//        when(userService.register(newUser)).thenThrow(new IllegalArgumentException("User details cannot not be empty or null"));
//
//        mockMvc.perform(post("/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newUser)))
//                        .andExpect(status().isBadRequest())
//                        .andExpect(status().reason("Invalid request content."))
//                        .andDo(print());
//    }


}
