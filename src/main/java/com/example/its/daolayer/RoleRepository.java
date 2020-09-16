package com.example.its.daolayer;

import com.example.its.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByName(String name);

}
