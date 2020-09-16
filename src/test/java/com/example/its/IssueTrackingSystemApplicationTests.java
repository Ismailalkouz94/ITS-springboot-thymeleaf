package com.example.its;

import com.example.its.daolayer.*;
import com.example.its.entity.*;
import com.example.its.service.IssuesService;
import com.example.its.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueTrackingSystemApplicationTests {
    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PositionRepository positionRepository;


    @Test
    public void contextLoads() {
        if (!statusRepository.existsById("1")) {
            Status s1 = new Status("1", "CREATED");
            Status s2 = new Status("2", "REOPEN");
            Status s3 = new Status("3", "RESOLVE");
            Status s4 = new Status("4", "CLOSED");
            statusRepository.save(s1);
            statusRepository.save(s2);
            statusRepository.save(s3);
            statusRepository.save(s4);
        }

        if (!typeRepository.existsById("1")) {
            Type type1 = new Type("1", "TASK");
            Type type2 = new Type("2", "ISSUE");
            typeRepository.save(type1);
            typeRepository.save(type2);
        }

        if (!positionRepository.existsById("1")) {
            Position position = new Position("1", "DEV", "Developer");
            Position position2 = new Position("2", "PM", "Project Manger");
            Position position3 = new Position("3", "QA", "Quality");
            positionRepository.save(position);
            positionRepository.save(position2);
            positionRepository.save(position3);
        }

        if (!roleRepository.existsById("1")) {
            Role role1 = new Role("1", "ADMIN");
            Role role2 = new Role("2", "USER");
            roleRepository.save(role1);
            roleRepository.save(role2);
        }

        if (!userRepository.existsById("1")){
            User user = new User("1", "ismail", "alkouz", "ismail@gamil.com", true, "admin", "$2a$10$zswmPMTPQx/haoyigz5z0uGCG37QjBHurv7LRMuEUnTUfVLgUXSu6", roleRepository.findById("1").get(), positionRepository.findById("1").get());
            userRepository.save(user);
        }

    }

}