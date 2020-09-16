package com.example.its.controller;

import com.example.its.entity.Issue;
import com.example.its.entity.User;
import com.example.its.service.IssuesService;
import com.example.its.service.UserService;
import com.example.its.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private UserService userService;
    @Autowired
    private IssuesService issuesService;

    @PostMapping("/save")
    public ModelAndView save(@Valid Issue issue, BindingResult bindingResult, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        issue.setOwner((User) session.getAttribute(Constants.CURRENT_USER));
//        User userExists = userService.findByUserName(user.getUserName());
//        if (userExists != null) {
//            bindingResult
//                    .rejectValue("userName", "error.user",
//                            "There is already a user registered with the user name provided");
//        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-issue");
        } else {
            issue.setId(null);
            issuesService.add(issue);
            modelAndView.addObject("successMessage", "Issue has been Created successfully");
            modelAndView.addObject("issue", new Issue());
            modelAndView.addObject("type", issuesService.findAllTypes());
            modelAndView.addObject("status", issuesService.findAllStatus());
            modelAndView.addObject("assignTo", userService.find());
            modelAndView.addObject("issue", new Issue());
            modelAndView.setViewName("add-issue");

        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        issuesService.delete(id);
        modelAndView.setViewName("redirect:/issues");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editview(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", issuesService.findAllTypes());
        modelAndView.addObject("status", issuesService.findAllStatus());
        modelAndView.addObject("assignTo", userService.find());
        modelAndView.addObject("issue", issuesService.find(id));
        modelAndView.addObject("action", "edit");

        modelAndView.setViewName("add-issue");

        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@Valid Issue issue, BindingResult bindingResult,HttpSession session) {
        issue.setOwner((User) session.getAttribute(Constants.CURRENT_USER));
        issuesService.edit(issue);
        return "redirect:/issues";
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", issuesService.findAllTypes());
        modelAndView.addObject("status", issuesService.findAllStatus());
        modelAndView.addObject("assignTo", userService.find());
        modelAndView.addObject("issue", issuesService.find(id));
        modelAndView.addObject("action", "view");

        modelAndView.setViewName("add-issue");

        return modelAndView;
    }

    @RequestMapping("/file/{fileName}")
    @ResponseBody
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {

        if (fileName.indexOf(".doc")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".docx")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".csv")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".ppt")>-1) response.setContentType("application/ppt");
        if (fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");
        if (fileName.indexOf(".zip")>-1) response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            issuesService.downloadFile(fileName,response).flushBuffer();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
