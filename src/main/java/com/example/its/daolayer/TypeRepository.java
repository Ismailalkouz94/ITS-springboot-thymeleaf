package com.example.its.daolayer;

import com.example.its.entity.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeRepository extends MongoRepository<Type,String> {
}
