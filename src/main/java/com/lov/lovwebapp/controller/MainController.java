package com.lov.lovwebapp.controller;


import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserService userService;
    private String email = "";
    // private boolean check;

    @Autowired
    public MainController(UserService userService) {

        this.userService = userService;
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/signup")
    public ModelAndView signUp() {
        return new ModelAndView("register", "user", new User());

    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest httpServletRequest) {
        userService.saveUser(user, httpServletRequest);
        email = user.getEmail();
        return new ModelAndView("redirect:/token-sent");
    }

    @RequestMapping("/redirectToLogin")
    public ModelAndView redirectToLogin() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/redirectToSignUp")
    public ModelAndView redirectToSignUp() {
        return new ModelAndView("redirect:/signup");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token, User user) {
        userService.verifyToken(user, token);
        email = user.getEmail();
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/token-sent")
    public String tokenSent(Model model) {
        model.addAttribute("email", email);
        return "token-sent";
    }
}
