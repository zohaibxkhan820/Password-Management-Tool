package com.password.tool.dao;


import com.password.tool.dao.interfaces.GroupDao;
import com.password.tool.dao.interfaces.GroupDaoWrapper;
import com.password.tool.dto.GroupDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateGroupException;
import com.password.tool.exception.GroupDoesNotExistException;
import com.password.tool.model.Group;
import com.password.tool.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("groupDaoWrapper")
public class GroupDaoWrapperImpl implements GroupDaoWrapper {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveGroup(GroupDto groupDto, UserDto userDto) throws DuplicateGroupException {
        User user = getUserFromBean(userDto);
        Group group = modelMapper.map(groupDto, Group.class);
        checkGorDuplicate(user, group.getGroupName());
        group.setGroupId(0);
        groupDao.save(group);
    }

    private User getUserFromBean(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private void checkGorDuplicate(User user, String groupName) throws DuplicateGroupException {
        if(groupDao.countByGroupNameAndUser(groupName , user)>0){
            throw new DuplicateGroupException("Group already Exist");
        }
    }

    @Override
    public List<GroupDto> getAllGroups(UserDto userDto) {
        User user = getUserFromBean(userDto);
        List<Group> groups = groupDao.findByUser(user);
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(group -> groupDtos.add(modelMapper.map(group , GroupDto.class)));
        return groupDtos;
    }

    @Override
    public GroupDto getGroupById(int groupId, UserDto userDto) throws GroupDoesNotExistException {
        User user = getUserFromBean(userDto);
        List<Group> groups = groupDao.findByGroupIdAndUser(groupId , user);
        if(groups.isEmpty()){
            throw new GroupDoesNotExistException("Group Does not Exist");
        }
        return modelMapper.map(groups.get(0) , GroupDto.class);
    }

    @Override
    public void updateGroupsById(int groupId, GroupDto groupDto, UserDto userDto) throws GroupDoesNotExistException, DuplicateGroupException {
        User user = getUserFromBean(userDto);
        GroupDto groupToUpdate = getGroupById(groupId , userDto);
        if(!groupToUpdate.getGroupName().equals(groupDto.getGroupName())){
            checkGorDuplicate(user , groupDto.getGroupName());
        }
        groupToUpdate.setGroupName(groupDto.getGroupName());
        groupToUpdate.setLastModifiedAt(new Date());
        Group group = modelMapper.map(groupToUpdate , Group.class);
        groupDao.save(group);
    }

    @Override
    public void deleteGroupById(int groupId, UserDto userDto) throws GroupDoesNotExistException {
        GroupDto groupDto = getGroupById(groupId , userDto);
        Group group = modelMapper.map(groupDto , Group.class);
        groupDao.delete(group);

    }
}
