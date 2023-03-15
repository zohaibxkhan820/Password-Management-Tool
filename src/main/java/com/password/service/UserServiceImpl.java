package com.password.tool.service;

import com.password.tool.dao.interfaces.UserDaoWrapper;
import com.password.tool.dto.UserDetailsBean;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import com.password.tool.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoWrapper userDaoWrapper;
    private static final Logger logger
            = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void saveUser(UserDto userDto) throws DuplicateUserException {
        logger.info("saving user bean");
        userDto.setCreatedAt(new Date());
        userDto.setLastModifiedAt(new Date());
        userDaoWrapper.saveUser(userDto);
        logger.info("User Bean saved Successfully");
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        logger.info("getting user by name");
        return userDaoWrapper.getUserByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("loading user by name");
        UserDto userDto = getUserByUserName(userName);
        return new UserDetailsBean(userDto);
    }
}
