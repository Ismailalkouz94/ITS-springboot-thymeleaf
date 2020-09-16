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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/save")
    public ModelAndView save(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (userRepository.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the Email provided");
        }

        if (userService.findByUserName(user.getUserName()) != null) {
            bindingResult.rejectValue("userName", "error.user",
                    "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("roles", userService.findRoles());
            modelAndView.addObject("positions", userService.findPositions());
//            modelAndView.addObject("user", new User());
            modelAndView.setViewName("add-user");
        } else {
            user.setId(null);
            userService.add(user);
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/users");

        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        userService.delete(id);
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editview(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        modelAndView.addObject("roles", userService.findRoles());
        modelAndView.addObject("positions", userService.findPositions());
        modelAndView.addObject("user", user);
        modelAndView.addObject("action", "edit");
        modelAndView.setViewName("add-user");
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@Valid User user, BindingResult bindingResult) {
        userService.edit(user);
        return "redirect:/users";
    }

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
