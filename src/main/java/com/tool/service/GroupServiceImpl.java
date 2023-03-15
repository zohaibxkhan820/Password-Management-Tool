package com.password.tool.service;


import com.password.tool.dao.interfaces.GroupDaoWrapper;
import com.password.tool.dto.AccountDto;
import com.password.tool.dto.GroupDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;
import com.password.tool.service.interfaces.AccountService;
import com.password.tool.service.interfaces.GroupService;
import com.password.tool.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDaoWrapper groupDaoWrapper;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    private static final Logger logger
            = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Override
    public void saveGroup(GroupDto groupDto) throws DuplicateGroupException {
        logger.info("saving the group bean");
        groupDto.setUser(getUserBean());
        groupDto.setCreatedAt(new Date());
        groupDto.setLastModifiedAt(new Date());
        groupDaoWrapper.saveGroup(groupDto, getUserBean());
        logger.info("group bean saved successfully");
    }

    @Override
    public List<GroupDto> getAllGroups()  {
        logger.info("group bean saved successfully");
        List<GroupDto> allGroups = groupDaoWrapper.getAllGroups(getUserBean());
        List<AccountDto> unAssignedAccounts = accountService.getUnassignedAccounts();
        GroupDto groupDto = new GroupDto("un_assigned");
        groupDto.setAccounts(unAssignedAccounts);
        allGroups.add(groupDto);
        return allGroups;

    }

    @Override
    public GroupDto getGroupById(int groupId) throws GroupDoesNotExistException {
        logger.info("getting group bean by id");
        return groupDaoWrapper.getGroupById(groupId , getUserBean());
    }

    private UserDto getUserBean()  {
        logger.info("getting user bean");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUserName(authentication.getName());
    }

    @Override
    public void updateGroupById(int groupId, GroupDto groupDto) throws DuplicateGroupException, GroupDoesNotExistException {
        logger.info("updating the group");
        groupDaoWrapper.updateGroupsById(groupId , groupDto, getUserBean());
        logger.info("group updated successfully");
    }

    @Override
    public void deleteGroupById(int groupId) throws GroupDoesNotExistException {
        groupDaoWrapper.deleteGroupById(groupId , getUserBean());
    }


}
