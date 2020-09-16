package com.example.its.controller;

import com.example.its.daolayer.StatusRepository;
import com.example.its.daolayer.TypeRepository;
import com.example.its.dto.ChangePasswordDTO;
import com.example.its.entity.Issue;
import com.example.its.entity.User;
import com.example.its.service.IssuesService;
import com.example.its.service.UserService;
import com.example.its.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author CrunchDroid
 */
@Controller
public class PageController {

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    UserService userService;

    @Autowired
    IssuesService issuesService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView plainPage(User user,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("plain-page");
        session.setAttribute("usersStatistics", userService.usersStatistics());
        return modelAndView;
    }

    @PostMapping("/home")
    public ModelAndView plainPage1(User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("plain-page");
        session.setAttribute(Constants.CURRENT_USER, userService.findByUserName(user.getUserName()));
        session.setAttribute("usersStatistics", userService.usersStatistics());
        return modelAndView;
    }


    @GetMapping("/users")
    public ModelAndView getUser() {

        ModelAndView modelAndView = new ModelAndView();
        List<User> user = userService.find();
        modelAndView.addObject("users", user);
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/addUser")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", userService.findRoles());
        modelAndView.addObject("positions", userService.findPositions());
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("add-user");
        return modelAndView;
    }

    @GetMapping("/issues")
    public ModelAndView issue(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user= (User) session.getAttribute(Constants.CURRENT_USER);
        modelAndView.addObject("issue", issuesService.findByAssigned(user.getId()));
        modelAndView.setViewName("issues");
        return modelAndView;
    }

    @GetMapping("/addIssue")
    public ModelAndView addIssue() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", issuesService.findAllTypes());
        modelAndView.addObject("status", issuesService.findAllStatus());
        modelAndView.addObject("assignTo", userService.find());
        modelAndView.addObject("issue", new Issue());
        modelAndView.setViewName("add-issue");
        return modelAndView;
    }
    @GetMapping("/changePassword")
    public ModelAndView changePasswordView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("changePassword", new ChangePasswordDTO());
        modelAndView.setViewName("change-password");
        return modelAndView;
    }


}
