package com.example.its.service.impl;


import com.example.its.daolayer.IssueRepository;
import com.example.its.daolayer.StatusRepository;
import com.example.its.daolayer.TypeRepository;
import com.example.its.daolayer.UserRepository;
import com.example.its.entity.Issue;
import com.example.its.entity.Status;
import com.example.its.entity.Type;
import com.example.its.entity.User;
import com.example.its.exception.IssueTrackingException;
import com.example.its.service.IssuesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssuesServiceImpl implements IssuesService {

    @Autowired
    private IssueRepository issuesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TypeRepository typeRepository;

//    @Autowired
//    private Queue queue;
//    @Autowired
//    private JmsTemplate jmsTemplate;

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads/";

    @Override
    public Issue add(Issue issue) {
//        ObjectWriter Obj = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper Obj = new ObjectMapper();
        issue.setOwner(userRepository.findById(issue.getOwner().getId()).get());
        issue.setAssignTo(userRepository.findById(issue.getAssignTo().getId()).get());
        issue.setType(typeRepository.findById(issue.getType().getId()).get());
        issue.setStatus(statusRepository.findById("1").get());
        if (issue.getAttachmentFiles().length>=1){
            uploadFiles(issue);
        }
//        Obj.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        issue=issuesRepository.save(issue);
//        Map map = prepareIssueData(issue);
//        jmsTemplate.convertAndSend(queue, map);
        return issuesRepository.save(issue);
    }

    private Map prepareIssueData(Issue issue) {
        Map map=new HashMap();
        map.put("ISSUE_ID",issue.getId());
        map.put("ISSUE_TITLE",issue.getTitle());
        map.put("ISSUE_DESC",issue.getDescription());
        map.put("ISSUE_OWNER",issue.getOwner().getFirstName()+" "+issue.getOwner().getLastName());
        map.put("ISSUE_ASSIGNED",issue.getAssignTo().getFirstName()+" "+issue.getAssignTo().getLastName());
        map.put("ISSUE_ASSIGNED_EMAIL",issue.getAssignTo().getEmail());
        return map;
    }

    private void uploadFiles(Issue issue) {
        checkAndCreateDir(uploadDirectory);
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : issue.getAttachmentFiles()) {
            String[] fileNameArr=file.getOriginalFilename().split("[.]");
            String fileName = fileNameArr[0] + " " + getDateTime() + "." + fileNameArr[1];
            Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
            fileNames.append(fileName + ",,");
            issue.setAttachmentPath(fileNames.toString());
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkAndCreateDir(String dir){
        File uploadDir=new File(dir);
        if(!uploadDir.exists()){
            new File(dir).mkdir();
        }
    }

    @Override
    public Issue edit(Issue issue) {
        if (!issuesRepository.existsById(issue.getId())) {
            throw new IssueTrackingException("ISSUE_NOT_FOUND");
        }
        Issue orignalIssue = issuesRepository.findById(issue.getId()).get();
        orignalIssue.setAssignTo(userRepository.findById(issue.getAssignTo().getId()).get());
        orignalIssue.setType(typeRepository.findById(issue.getType().getId()).get());
        orignalIssue.setStatus(statusRepository.findById(issue.getStatus().getId()).get());
        return issuesRepository.save(orignalIssue);
    }

    @Override
    public boolean delete(String id) {
        if (!issuesRepository.existsById(id)) {
            throw new IssueTrackingException("ISSUE_NOT_FOUND");
        } else {
            issuesRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public List<Issue> findAll() {
        return issuesRepository.findAll();
    }

    @Override
    public Issue find(String id) {
        if (!issuesRepository.existsById(id)) {
            throw new IssueTrackingException("ISSUE_NOT_FOUND");
        }
        return issuesRepository.findById(id).get();
    }

    @Override
    public List<Issue> findByUser(String id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new IssueTrackingException("USER_NOT_FOUND");
        } else {
            return issuesRepository.findByOwner(user);
        }
    }

    @Override
    public List<Issue> findByAssigned(String id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new IssueTrackingException("USER_NOT_FOUND");
        } else {
            return issuesRepository.findByAssignTo(user);
        }
    }

    @Override
    public List<Type> findAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public List<Status> findAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public List<Issue> issuesFilter(String id, int filterId) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new IssueTrackingException("USER_NOT_FOUND");
        }
        List<Issue> issuesList = new ArrayList<>();
        switch (filterId) {
            case 1:
                issuesList = issuesRepository.findByOwner(user);
                break;
            case 2:
                issuesList = issuesRepository.findByAssignTo(user);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                Status status = new Status();
                if (filterId == 3) {
                    status = statusRepository.findById("1").get();
                } else if (filterId == 4) {
                    status = statusRepository.findById("3").get();
                } else if (filterId == 5) {
                    status = statusRepository.findById("4").get();
                } else if (filterId == 6) {
                    status = statusRepository.findById("2").get();
                }
                issuesList = issuesRepository.findByAssignToAndStatus(user, status);
                break;
        }

        return issuesList;
    }

    @Override
    public HttpServletResponse downloadFile(String fileName, HttpServletResponse response) {

        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(uploadDirectory+fileName);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private  String  getDateTime(){
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
        LocalDateTime dateTime= LocalDateTime.now();
        return formatter.format(dateTime);
    }
}
