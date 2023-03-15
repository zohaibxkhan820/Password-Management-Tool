package com.password.tool.service.interfaces;



import com.password.tool.dto.GroupDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;

import java.util.List;

public interface GroupService {
    void saveGroup(GroupDto groupDto) throws DuplicateGroupException;
    List<GroupDto> getAllGroups() ;
    GroupDto getGroupById(int groupId) throws GroupDoesNotExistException;
    void updateGroupById(int groupId , GroupDto groupDto) throws DuplicateGroupException, GroupDoesNotExistException;

    void deleteGroupById(int i) throws GroupDoesNotExistException;
}
