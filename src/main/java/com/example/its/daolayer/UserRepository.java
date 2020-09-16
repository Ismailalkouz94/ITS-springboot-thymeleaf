package com.example.its.daolayer;

import com.example.its.entity.Role;
import com.example.its.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    User findByUserName(String userName);
    User findByEmail(String email);
    void deleteById(String id);
    Optional<User> findByUserNameAndIdNot(String userName, String id);
    Optional<User> findByEmailAndIdNot(String email,String id);
    Long countUserByActive(Boolean active);
    Long countUserByRole(Role role);

}
