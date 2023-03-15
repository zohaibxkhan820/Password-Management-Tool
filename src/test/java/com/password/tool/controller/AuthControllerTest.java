package com.password.tool.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.password.tool.dto.UserDto;
import com.password.tool.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class)
public class AuthControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    UserDto userDto;

    @Before
    public void setUp(){
        userDto = new UserDto();
    }



    @Test
    @DisplayName("loginUser Should redirect to all groups if user is logged in")
    @WithMockUser
    public void loginUserShouldRedirectToAllGroupsIfUserIsLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/login"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    @DisplayName("user Should redirect to all groups if user is logged in")
    @WithMockUser
    public void userShouldRedirectToAllGroupsIfUserIsLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(view().name("redirect:/groups"));
    }

}
