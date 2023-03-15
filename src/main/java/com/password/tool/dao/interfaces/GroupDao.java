package com.password.tool.dao.interfaces;


import com.password.tool.model.Group;
import com.password.tool.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface GroupDao extends CrudRepository<Group, Integer> {
    int countByGroupNameAndUser(String groupName, User user);
    List<Group> findByUser(User user);
    List<Group> findByGroupIdAndUser(int groupId , User user);
}
