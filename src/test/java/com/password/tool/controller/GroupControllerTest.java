package com.password.tool.controller;


import com.password.tool.dto.GroupDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;
import com.password.tool.service.interfaces.GroupService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GroupController.class)
@WithMockUser
public class GroupControllerTest {
    @MockBean
    GroupService groupService;
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    GroupDto groupDto;

    @Before
    public void setUp(){
        groupDto = new GroupDto();
    }

    @Test
    @DisplayName("getGroupById Should Give addGroup page If Group Exist")
    public void getGroupByIdShouldGiveAddGroupPageIfAccountExist() throws Exception {
        when(groupService.getGroupById(anyInt())).thenReturn(groupDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/group/2"))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("addGroup Should Give addGroup page")
    public void addGroupShouldReturnAddGroupPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/group"))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("getAllGroups Should Give all groups on viewAccounts page")
    public void getAllGroupsShouldGiveAllGroupsOnViewAccountPage() throws Exception {
        when(groupService.getAllGroups()).thenReturn(List.of(groupDto));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups"))
                .andExpect(view().name("viewAccounts"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/viewAccounts.jsp"));
    }

    @Test
    @DisplayName("getGroupById Should Give Error page If Group not Exist")
    public void getGroupByIdShouldGiveErrorPageIfGroupNotExist() throws Exception {
        doThrow(GroupDoesNotExistException.class).when(groupService).getGroupById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/group/2"))
                .andExpect(view().name("error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/error.jsp"));
    }

    @Test
    @DisplayName("deleteGroup Should Give Ok Status If Group Exist")
    public void deleteShouldDeleteGroupAndRedirectToAllGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group/delete/2"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    @DisplayName("deleteGroup Should Give error page If Group not Exist")
    public void deleteGroupShouldGiveErrorPageIfGroupNotExist() throws Exception {
        doThrow(GroupDoesNotExistException.class).when(groupService).deleteGroupById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group/delete/2"))
                .andExpect(view().name("error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/error.jsp"));
    }

    @Test
    @DisplayName("saveGroup Should return same page with error for invalid group")
    public void saveGroupShouldReturnSamePageWithErrorForInvalidGroup() throws Exception {
        GroupDto groupDto = new GroupDto("");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("saveGroup Should Give same page with error If group is duplicate")
    public void saveGroupShouldGiveSamePageWithErrorForDuplicateGroup() throws Exception {
        GroupDto groupDto = new GroupDto("cse");
        doThrow(DuplicateGroupException.class).when(groupService).saveGroup(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("saveGroup Should Give Ok Status If group saved")
    public void saveGroupShouldSaveGroupAndRedirectToAllGroups() throws Exception {
        GroupDto groupDto = new GroupDto("cse");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    @DisplayName("updateGroup Should return same page with error for invalid group")
    public void updateGroupShouldReturnSamePageWithErrorForInvalidGroup() throws Exception {
        GroupDto groupDto = new GroupDto("");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group/1")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("updateGroup Should Give same page with error If group is duplicate")
    public void updateGroupShouldGiveSamePageWithErrorForDuplicateGroup() throws Exception {
        GroupDto groupDto = new GroupDto("cse");
        doThrow(DuplicateGroupException.class).when(groupService).updateGroupById(anyInt(),any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group/1")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("addGroup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addGroup.jsp"));
    }

    @Test
    @DisplayName("updateGroup Should Give Ok Status If group saved")
    public void updateGroupShouldSaveGroupAndRedirectToAllGroups() throws Exception {
        GroupDto groupDto = new GroupDto("cse");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/group/1")
                        .flashAttr("groupBean" , groupDto))
                .andExpect(view().name("redirect:/groups"));
    }

}
