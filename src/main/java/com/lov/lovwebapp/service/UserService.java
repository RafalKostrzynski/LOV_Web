package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser(User user, HttpServletRequest request);

    void verifyToken(User user, String token);

    List<User> getAllUsers();

    User getUserByName(String username);

    User getUserByID(long id);
}
