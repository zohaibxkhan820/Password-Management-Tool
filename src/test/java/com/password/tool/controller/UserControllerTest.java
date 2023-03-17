package com.password.tool.controller;

import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import com.password.tool.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@WithMockUser
public class UserControllerTest {

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
    @DisplayName("addUser Should Give register page")
    public void addUserShouldReturnRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/register"))
                .andExpect(view().name("register"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/register.jsp"));
    }
    @Test
    @DisplayName("saveUserShould Give Bad Request Status If User is invalid")
    public void saveUserShouldGiveSamePageWithErrorForInvalidUser() throws Exception {
        UserDto userDto = new UserDto("","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register"))
                .andExpect(view().name("register"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/register.jsp"));
    }

    @Test
    @DisplayName("saveUser Should Give same page with error If User is duplicate")
    public void saveUserShouldGiveSamePageWithErrorForDuplicateUser() throws Exception {
        UserDto userDto = new UserDto("Giri Dhara","cse","Giri@1234");
        doThrow(DuplicateUserException.class).when(userService).saveUser(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("userBean",userDto))
                .andExpect(view().name("register"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/register.jsp"));
    }

    @Test
    @DisplayName("saveUser Should redirect to login page")
    public void saveUserShouldRedirectToLoginPage() throws Exception {
        UserDto userDto = new UserDto("Giri Dhara","cse","Giri@1234");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("userBean",userDto))
                .andExpect(view().name("redirect:login"));
    }

}
