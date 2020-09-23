package com.example.its.controller;

import com.example.its.daolayer.UserRepository;
import com.example.its.dto.ChangePasswordDTO;
import com.example.its.entity.User;
import com.example.its.exception.IssueTrackingException;
import com.example.its.service.UserService;
import com.example.its.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class CredentialsController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid ChangePasswordDTO changePassword, HttpSession session) {
        try {
            User user = (User) session.getAttribute(Constants.CURRENT_USER);
            changePassword.setUserId(user.getId());
            userService.changePassword(changePassword);
        } catch (IssueTrackingException e) {
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage(
                            e.getMessage(), null, Locale.US));

        }
        return ResponseEntity.ok(messageSource.getMessage("change.password.success", null, Locale.US));
    }

}
