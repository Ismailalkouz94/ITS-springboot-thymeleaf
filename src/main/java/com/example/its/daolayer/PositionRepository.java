package com.example.its.daolayer;

import com.example.its.entity.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepository extends MongoRepository<Position,String> {
}
