package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.MailSenderService;
import com.lov.lovwebapp.service.UserService;
import com.lov.lovwebapp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Primary
@Service
public class UserServiceImplementation implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    private MailSenderService mailSenderService;

    @Autowired
    public UserServiceImplementation(UserRepo userRepo, PasswordEncoder passwordEncoder, VerificationTokenService verificationTokenService, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.mailSenderService = mailSenderService;

//        userRepo.save(new User("admin", "admin@wp.pl",
//                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
//                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 0, true));
//
//        userRepo.save(new User("admin123", "admin123@wp.pl",
//                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
//                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 0, true));

    }

    @Override
    public void saveUser(User user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" + verificationTokenService.sendToken(user);

        try {
            mailSenderService.sendMail(user.getEmail(),
                    "Verification token",
                    url,
                    false
            );
        } catch (MessagingException e) {
        }
    }

    @Override
    public void verifyToken(User user, String token) {
        verificationTokenService.verifyToken(user, token);
        userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByName(String username) {
        return userRepo.findAllByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findAllByUsername(s);
    }
}
