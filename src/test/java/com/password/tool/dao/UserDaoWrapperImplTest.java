package com.password.tool.dao;

import com.password.tool.dao.interfaces.UserDao;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import com.password.tool.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoWrapperImplTest {

    @InjectMocks
    UserDaoWrapperImpl userDaoWrapper;

    @Mock
    UserDao userDao;
    @Mock
    ModelMapper modelMapper;
    @Mock
    User user;
    UserDto userDto;
    @Before
    public void setUp(){
        userDto = new UserDto("","","");
        user = new User("" ,"","");
        when(modelMapper.map(userDto , User.class)).thenReturn(user);
        when(modelMapper.map(user , UserDto.class)).thenReturn(userDto);
    }

    @Test
    @DisplayName("UserDaoWrapper should return user by user name")
    public void userDaoWrapperShouldReturnUserByUserName(){
        when(userDao.findByUserName(anyString())).thenReturn(List.of(user));
        Assertions.assertDoesNotThrow(()-> userDaoWrapper.getUserByUserName(""));
    }

    @Test
    @DisplayName("UserDaoWrapper should return user by user name")
    public void userDaoWrapperShouldReturnUserByUserName1(){
        when(userDao.findByUserName(anyString())).thenReturn(List.of());
        Assertions.assertThrows(UsernameNotFoundException.class ,()-> userDaoWrapper.getUserByUserName(""));
    }

    @Test
    @DisplayName("UserDaoWrapper should save unique user")
    public void userDaoWrapperShouldSaveUniqueUser(){
        when(userDao.countByUserName(anyString())).thenReturn(0);
        Assertions.assertDoesNotThrow(()-> userDaoWrapper.saveUser(userDto));
    }

    @Test
    @DisplayName("UserDaoWrapper should save should throw exception for duplicate user")
    public void userDaoWrapperShouldThrowExceptionForDuplicateUser(){
        when(userDao.countByUserName(anyString())).thenReturn(1);
        Assertions.assertThrows(DuplicateUserException.class ,()-> userDaoWrapper.saveUser(userDto));
    }

}
