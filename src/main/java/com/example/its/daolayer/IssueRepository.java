package com.example.its.daolayer;

import com.example.its.entity.Issue;
import com.example.its.entity.Status;
import com.example.its.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IssueRepository extends MongoRepository<Issue,String> {
    void deleteById(Issue issues);
    List<Issue> findByOwner(User Owner);
    List<Issue> findByAssignTo(User user);
    List<Issue> findByAssignToAndStatus(User user, Status status);
}
