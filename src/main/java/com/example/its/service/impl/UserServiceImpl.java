package com.example.its.service.impl;


import com.example.its.daolayer.PositionRepository;
import com.example.its.daolayer.RoleRepository;
import com.example.its.daolayer.UserRepository;
import com.example.its.dto.ChangePasswordDTO;
import com.example.its.entity.Position;
import com.example.its.entity.Role;
import com.example.its.entity.User;
import com.example.its.exception.IssueTrackingException;
import com.example.its.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User add(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IssueTrackingException("USER NAME IS EXIST");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IssueTrackingException("EMAIL IS EXIST");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPosition(positionRepository.findById(user.getPosition().getId()).get());
        user.setRole(roleRepository.findById(user.getRole().getId()).get());
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        if (userRepository.findByUserNameAndIdNot(user.getUserName(),user.getId()).isPresent()) {
            throw new IssueTrackingException("USER NAME IS EXIST");
        }
        if (userRepository.findByEmailAndIdNot(user.getEmail(),user.getId()).isPresent() ) {
            throw new IssueTrackingException("EMAIL IS EXIST");
        }
        user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        user.setPosition(positionRepository.findById(user.getPosition().getId()).get());
        user.setRole(roleRepository.findById(user.getRole().getId()).get());
//        user.setActive(userRepository.findById(user.getId()).get().getActive());
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        User user = null;
        user = userRepository.findByUserName(userName);
            return user;
    }

    @Override
    public List<User> find() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User delete(String id) {
        User user = null;
        if (!userRepository.existsById(id)) {
            throw new IssueTrackingException("USER_NOT_FOUND");
        } else {
            userRepository.deleteById(id);
        }
        return user;
    }

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Position> findPositions() {
        return positionRepository.findAll();
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {

        if (!userRepository.existsById(changePasswordDTO.getUserId())) {
            throw new IssueTrackingException("USER_NOT_FOUND");
        }
        User user = userRepository.findById(changePasswordDTO.getUserId()).get();
        if (!changePasswordDTO.getNewPassword().equalsIgnoreCase(changePasswordDTO.getConfirmPassword())){
            throw new IssueTrackingException("NEW_PASSWORD_AND_CONFIRMED_PASSWORD_NOT_SIMILAR");
        }
        if (bCryptPasswordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        }else {
            throw new IssueTrackingException("PASSWORD_NOT_MATCH");
        }

    }

    @Override
    public Map usersStatistics() {
        Map<String,Long> result=new HashMap<>();
        result.put("totalUsers",userRepository.count());
        result.put("activeUsers",userRepository.countUserByActive(true));
        result.put("disabledUsers",userRepository.countUserByActive(false));
        result.put("adminUsers",userRepository.countUserByRole(roleRepository.findById("1").get()));
        return result;
    }
}
