package com.password.tool.dao.interfaces;


import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;

public interface UserDaoWrapper {
    void saveUser(UserDto userDto) throws DuplicateUserException;
    UserDto getUserByUserName(String userName) ;
}
