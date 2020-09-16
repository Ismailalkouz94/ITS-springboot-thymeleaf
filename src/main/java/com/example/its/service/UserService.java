package com.example.its.service;

import com.example.its.dto.ChangePasswordDTO;
import com.example.its.entity.Position;
import com.example.its.entity.Role;
import com.example.its.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User add(User user);

    User edit(User user);

    User findByUserName(String userName);

    List<User> find();

    User findById(String id);

    User delete(String id);

    List<Role> findRoles();

    List<Position> findPositions();

    void changePassword(ChangePasswordDTO changePasswordDTO);

    Map usersStatistics();

}
