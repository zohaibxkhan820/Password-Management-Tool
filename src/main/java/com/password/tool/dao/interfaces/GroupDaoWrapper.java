package com.password.tool.dao.interfaces;

import com.password.tool.dto.GroupDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;

import java.util.List;

public interface GroupDaoWrapper {
    void saveGroup(GroupDto group , UserDto userDto) throws DuplicateGroupException;
    List<GroupDto> getAllGroups(UserDto userDto) ;
    GroupDto getGroupById(int groupId , UserDto userDto) throws GroupDoesNotExistException;
    void updateGroupsById(int groupId , GroupDto groupDto, UserDto userDto) throws GroupDoesNotExistException, DuplicateGroupException;

    void deleteGroupById(int i, UserDto userDto) throws GroupDoesNotExistException;
}
