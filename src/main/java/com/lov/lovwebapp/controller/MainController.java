package com.lov.lovwebapp.controller;


import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
   // private boolean check;

    @Autowired
    public MainController(UserRepo userRepo, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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

    @RequestMapping("/signUp")
    public ModelAndView signUp(Model model) {
 //      model.addAttribute("check",check);
        return new ModelAndView("register", "user", new User());

    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest httpServletRequest) {
      //  if(check) {
            try {
                userService.addNewUser(user, httpServletRequest);
            } catch (javax.mail.MessagingException e) {
            }
            return new ModelAndView("redirect:/login");
     //   }
     //   return null;
    }

    @RequestMapping("/redirectToLogin")
    public ModelAndView redirectToLogin(){return new ModelAndView("redirect:/login");}
    @RequestMapping("/redirectToSignUp")
    public ModelAndView redirectToSignUp(){return new ModelAndView("redirect:/signUp");}

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token) {
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }
}
