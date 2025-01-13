package com.thegymgoers_java.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.service.GymGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GymGroupControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GymGroupService gymGroupService;

    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        user = new User("testname", "testemail@dom.com", "fakepass");
        user2 = new User("testname2", "testemail2@dom.com", "fakepass");
    }

    @Test
    void shouldReturn200CreatingNewGroup() throws Exception{

        mockMvc.perform(post("/users/{username}/workouts", user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andDo(print());

    }

}
