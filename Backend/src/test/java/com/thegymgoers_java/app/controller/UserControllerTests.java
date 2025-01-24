package com.thegymgoers_java.app.controller;

import org.junit.jupiter.api.Nested;
import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.model.Workout;
import com.thegymgoers_java.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Nested
    class getWorkout {


        @Test
        @WithMockUser(username = "testname", roles = {"USER"})
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
        void shouldReturnNullInvalidUsername() throws Exception {
            List<Workout> workoutList = new ArrayList<>();
            when(userService.getWorkouts(user.getUsername()))
                    .thenReturn(null);

            mockMvc.perform(get("/users/{username}/workouts", user.getUsername())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""))
                    .andDo(print());
        }

//        @Test
//        @WithMockUser(username = "testname", roles = {"USER"})
//        void shouldReturnUsersWorkoutList() throws Exception {
//            List<Workout> workoutList = new ArrayList<>();
//            when(userService.getWorkouts(user.getUsername()))
//                    .thenReturn(workoutList);
//
//            mockMvc.perform(get("/users/{username}/workouts", user.getUsername())
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(user)))
//                    .andExpect(status().isOk())
//                    .andExpect(content().json("[]"))
//                    .andDo(print());
//        }


    }

    @Nested
    class addWorkout {


        @Test
        @WithMockUser(username = "testname", roles = {"USER"})
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

        @Test
        @WithMockUser(username = "testname", roles = {"USER"})
        void shouldReturnValidationMessageWhenExerciseNameIsEmpty() throws Exception {
            // Mocking the invalid json request body with an empty exerciseName
            String invalidJson = "{ \"exercises\": [ { \"exerciseName\": \"\", \"reps\": 10, \"sets\": 3, \"weight\": 50 } ], \"dateCreated\": \"2023-10-01\" }";

            mockMvc.perform(post("/users/testname/workouts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidJson))
                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.errors.exerciseName").value("Exercise needs a name"))
                    .andDo(print());
        }
        @Test
        @WithMockUser(username = "testname", roles = {"USER"})
        void shouldReturnValidationMessageWhenExercisesListIsEmpty() throws Exception {
            // Mocking the invalid json request body with an empty exercises list
            String invalidJson = "{ \"exercises\": [], \"dateCreated\": \"2023-10-01\" }";

            mockMvc.perform(post("/users/testname/workouts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.exercises").value("Workout needs valid exercises"))
                    .andDo(print());
        }

    }

    @Nested
    class deleteWorkout {

        /*
        @Test
        @WithMockUser(username = "testname", roles = {"USER"})
        void shouldReturnEmptyArray() throws Exception {
            List<Workout> workoutList = new ArrayList<>();
            Workout workout = new Workout();
            workout.set_id("1");
            workoutList.add(workout);

            when(userService.getWorkouts(user.getUsername()))
                    .thenReturn(workoutList);

            mockMvc.perform(delete("/users/{username}/workouts/{workoutID}", user.getUsername(), workoutList.get(0).get_id())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"))
                    .andDo(print());
        }
*/
    }
}



