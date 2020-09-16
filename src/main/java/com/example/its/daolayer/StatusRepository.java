package com.example.its.daolayer;

import com.example.its.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status,String> {
}
