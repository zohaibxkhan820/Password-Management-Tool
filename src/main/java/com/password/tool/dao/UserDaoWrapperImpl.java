package com.password.tool.dao;


import com.password.tool.dao.interfaces.UserDao;
import com.password.tool.dao.interfaces.UserDaoWrapper;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import com.password.tool.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoWrapper")
public class UserDaoWrapperImpl implements UserDaoWrapper {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveUser(UserDto userDto) throws DuplicateUserException {
        User user = modelMapper.map(userDto, User.class);
        if(userDao.countByUserName(user.getUserName())>0){
            throw new DuplicateUserException("User with this UserName already Exist");
        }
        user.setUserId(0);
        userDao.save(user);
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        List<User> user = userDao.findByUserName(userName);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Invalid UserName / Password");
        }
        return modelMapper.map(user.get(0) , UserDto.class);
    }
}
