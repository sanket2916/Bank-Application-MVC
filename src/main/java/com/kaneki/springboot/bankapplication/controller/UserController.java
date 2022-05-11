package com.kaneki.springboot.bankapplication.controller;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.entity.User;
import com.kaneki.springboot.bankapplication.service.RestService;
import com.kaneki.springboot.bankapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestService restService;

    @Autowired
    private UserService userService;

    public UserController() {
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = restService.findAllUser();
        model.addAttribute("users", users);
        return "list-users";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("crmUser", new CrmUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmUser") CrmUser crmUser,
            BindingResult bindingResult,
            Model model) {

        String userName = crmUser.getUserName();
        if (bindingResult.hasErrors()){
            return "registration-form";
        }

        try {
            restService.saveUser(crmUser);
            return "registration-confirmation";
        } catch (RuntimeException exp) {
            model.addAttribute("crmUser", new CrmUser());
            model.addAttribute("registrationError", "User name already exists.");
            return "registration-form";
        }
    }
}
