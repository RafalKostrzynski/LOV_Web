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
import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;
    private String email;

    @Autowired
    public LoginController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main(Model model, Principal principal) {
        model.addAttribute("points", userService.getUserByName(principal.getName()).getPoints());
        return "main";
    }

    @RequestMapping("/signup")
    public ModelAndView signUp() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping("/signuperror")
    public ModelAndView signUpError() {
        return new ModelAndView("registererror", "user", new User());
    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest httpServletRequest) {
        if(checkData(user)) {
            if (!checkIfTaken(user)) {
                userService.saveUser(user, httpServletRequest);
                email = user.getEmail();
                return new ModelAndView("redirect:/token-sent");
            }
            return new ModelAndView("redirect:/signuperrortaken");
        }
        return new ModelAndView("redirect:/signuperror");
    }

    @RequestMapping("/signuperrortaken")
    public ModelAndView signUpErrorTaken() {
        return new ModelAndView("registererrortaken", "user", new User());
    }

    @RequestMapping("/token-sent")
    public String tokenSent(Model model) {
        model.addAttribute("email", email);
        return "token-sent";
    }

    private boolean checkData(User user) {
        String patternPassword="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{9,}$";
        String patternUsername="^(?=\\S+$).{3,}$";

        return user.getPassword().matches(patternPassword) && user.getPassword().equals(user.getPasswordRepeat())
                && user.getUsername().matches(patternUsername);
    }

    private boolean checkIfTaken(User user) {
        List<User> userList = userService.getAllUsers();
        return userList.stream().anyMatch(e -> e.getUsername().equals(user.getUsername()) || e.getEmail().equals(user.getEmail()));
    }

    @RequestMapping("/redirectToLogin")
    public ModelAndView redirectToLogin() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/redirectToLogout")
    public ModelAndView redirectToLogout() {
        return new ModelAndView("redirect:/logout");
    }

    @RequestMapping("/redirectToSignUp")
    public ModelAndView redirectToSignUp() {
        return new ModelAndView("redirect:/signup");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token, User user) {
        userService.verifyToken(user, token);
        return new ModelAndView("redirect:/login");
    }
}
