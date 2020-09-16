package com.example.its.service;

import com.example.its.entity.Issue;
import com.example.its.entity.Status;
import com.example.its.entity.Type;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IssuesService {

    Issue add(Issue issue);
    Issue edit(Issue issue);
    boolean delete(String id);
    List<Issue> findAll();
    Issue find(String id);
    List<Issue> findByUser(String id);
    List<Issue> findByAssigned(String id);
    List<Type> findAllTypes();
    List<Status> findAllStatus();
    List<Issue> issuesFilter(String id,int filterId);
    HttpServletResponse downloadFile(String fileName, HttpServletResponse response);

}
