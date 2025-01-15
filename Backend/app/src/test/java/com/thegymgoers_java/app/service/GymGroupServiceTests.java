package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.model.GymGroup;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.payload.request.NewGymGroupRequest;
import com.thegymgoers_java.app.repository.GymGroupRepository;
import com.thegymgoers_java.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class GymGroupServiceTests {


    @Mock
    private GymGroupRepository gymGroupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GymGroupService gymGroupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGymGroup_Success() throws Exception {
        NewGymGroupRequest request = new NewGymGroupRequest();
        request.setGroupName("Test Group");

        User user = new User("testuser", "test@email.com", "testpass");
        user.setId("1");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(gymGroupRepository.findByGroupName(anyString())).thenReturn(Optional.empty());
        when(gymGroupRepository.save(any(GymGroup.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GymGroup result = gymGroupService.createGymGroup("testuser", request);

        assertNotNull(result);
        assertEquals("Test Group", result.getGroupName());
        verify(gymGroupRepository, times(1)).save(any(GymGroup.class));
    }

    @Test
    void testCreateGymGroup_UsernameNullOrEmpty() {
        NewGymGroupRequest request = new NewGymGroupRequest();
        request.setGroupName("Test Group");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gymGroupService.createGymGroup(null, request);
        });

        assertEquals("User details cannot not be empty or null", exception.getMessage());
    }

    @Test
    void testCreateGymGroup_GroupNameNullOrEmpty() {
        NewGymGroupRequest request = new NewGymGroupRequest();
        request.setGroupName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gymGroupService.createGymGroup("testuser", request);
        });

        assertEquals("GymGroup must have a name", exception.getMessage());
    }

    @Test
    void testCreateGymGroup_GroupNameExists() {
        NewGymGroupRequest request = new NewGymGroupRequest();
        request.setGroupName("Test Group");

        when(gymGroupRepository.findByGroupName(anyString())).thenReturn(Optional.of(new GymGroup()));

        Exception exception = assertThrows(Exception.class, () -> {
            gymGroupService.createGymGroup("testuser", request);
        });

        assertEquals("GymGroup with that name exists", exception.getMessage());
    }

    @Test
    void testCreateGymGroup_UserNotFound() {
        NewGymGroupRequest request = new NewGymGroupRequest();
        request.setGroupName("Test Group");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            gymGroupService.createGymGroup("testuser", request);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
