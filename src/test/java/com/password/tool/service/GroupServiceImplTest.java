package com.password.tool.service;

import com.password.tool.dao.interfaces.GroupDaoWrapper;
import com.password.tool.dto.GroupDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;
import com.password.tool.service.interfaces.AccountService;
import com.password.tool.service.interfaces.UserService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {

    @InjectMocks
    GroupServiceImpl groupService;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @Mock
    UserService userService;
    @Mock
    GroupDaoWrapper groupDaoWrapper;
    @Mock
    AccountService accountService;
    UserDto userBean;
    GroupDto groupBean;
    private static MockedStatic<SecurityContextHolder> mockedSettings;

    @BeforeClass
    public static void init(){
            mockedSettings = mockStatic(SecurityContextHolder.class);
    }

    @AfterClass
    public static void close(){
        mockedSettings.close();
    }

    @Before
    public void setUp() {
        userBean = new UserDto();
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("name");
        when(userService.getUserByUserName(any())).thenReturn(userBean);
        groupBean = new GroupDto("abc");
    }

    @Test
    @DisplayName("Group service should save unique and encrypted group")
    public void groupServiceShouldSaveUniqueAndEncryptedGroup() {
        Assertions.assertDoesNotThrow(()->groupService.saveGroup(groupBean));
    }
    @Test
    @DisplayName("Group service should check for duplicate group")
    public void saveGroupShouldCheckForDuplicateGroup() throws DuplicateGroupException {
        doThrow(DuplicateGroupException.class).when(groupDaoWrapper).saveGroup(groupBean , userBean);
        Assertions.assertThrows(DuplicateGroupException.class,()->groupService.saveGroup(groupBean));
    }


    @Test
    @DisplayName("Group service getGroupById should return group if found")
    public void getGroupByIdShouldReturnGroupIfFound() throws GroupDoesNotExistException {
        when(groupDaoWrapper.getGroupById(anyInt(),any())).thenReturn(groupBean);
        Assertions.assertDoesNotThrow(()->groupService.getGroupById(groupBean.getGroupId()));
    }

    @Test
    @DisplayName("getGroupById should throw exception if group not found")
    public void getGroupByIdShouldThrowExceptionIfGroupNotFound() throws GroupDoesNotExistException {
        doThrow(GroupDoesNotExistException.class ).when(groupDaoWrapper).getGroupById(anyInt() , any());
        Assertions.assertThrows(GroupDoesNotExistException.class,()->groupService.getGroupById(groupBean.getGroupId()));
    }

    @Test
    @DisplayName("updateGroupById should check for duplicate group name if new group name is different")
    public void updateGroupByIdShouldCheckForDuplicateGroupNameIfGroupNameIsDifferent(){
        Assertions.assertDoesNotThrow(()->groupService.updateGroupById(1 , new GroupDto(" sdv")));

    }

    @Test
    @DisplayName("updateGroupById should check for duplicate and throw exception id duplicate")
    public void updateGroupByIdShouldCheckForDuplicateGroupNameAndThrowExceptionIfDuplicate() throws DuplicateGroupException, GroupDoesNotExistException {
        doThrow(DuplicateGroupException.class ).when(groupDaoWrapper).updateGroupsById(anyInt() ,any() , any());
        Assertions.assertThrows(DuplicateGroupException.class,()->groupService.updateGroupById(1 , new GroupDto(" sfds")));
    }

    @Test
    @DisplayName("updateGroupById should throw exception if group does not exist")
    public void updateGroupByIdShouldThrowExceptionIfGroupDoesNotExist() throws GroupDoesNotExistException, DuplicateGroupException {
        doThrow(GroupDoesNotExistException.class ).when(groupDaoWrapper).updateGroupsById(anyInt() , any() ,any());
        Assertions.assertThrows(GroupDoesNotExistException.class,()->groupService.updateGroupById(1 , new GroupDto(" sfds")));
    }
    @Test
    @DisplayName("deleteGroupById should throw exception if group does not exist")
    public void deleteByIdShouldThrowExceptionIfGroupDoesNotExist() throws GroupDoesNotExistException {
        doThrow(GroupDoesNotExistException.class ).when(groupDaoWrapper).deleteGroupById(anyInt() , any());
        Assertions.assertThrows(GroupDoesNotExistException.class,()->groupService.deleteGroupById(1));
    }

    @Test
    @DisplayName("deleteGroupById should delete group if exist")
    public void deleteByIdShouldDeleteGroupIfExist(){
        Assertions.assertDoesNotThrow(()->groupService.deleteGroupById(1 ));
    }

    @Test
    @DisplayName("getAllGroups should return all groups")
    public void getAllGroupsShouldReturnAllGroups(){
        Assertions.assertDoesNotThrow(()->groupService.getAllGroups( ));
    }


}
