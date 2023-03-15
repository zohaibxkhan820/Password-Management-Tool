package com.password.tool.service.interfaces;

import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(UserDto userDto) throws DuplicateUserException;
    UserDto getUserByUserName(String userName);
}
