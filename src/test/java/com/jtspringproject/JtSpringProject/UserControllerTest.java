package com.jtspringproject.JtSpringProject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jtspringproject.JtSpringProject.controller.UserController;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.userService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private userService userService;

    @BeforeEach
    public void setUp() {
        // Setup mock behavior if needed before each test
    }

    @Test
    public void testUserLoginValidate() throws Exception {
        // Mock user data
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("testPassword");
        mockUser.setRole("ROLE_USER");

        // Mock userService behavior
        when(userService.checkLogin("testUser", "testPassword")).thenReturn(mockUser);

        // Perform the request and assert the response
        mockMvc.perform(post("/userloginvalidate")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", mockUser))
                .andExpect(model().attributeExists("products"));

        // Optionally, you can verify interactions with the userService
        verify(userService, times(1)).checkLogin("testUser", "testPassword");
    }
}
