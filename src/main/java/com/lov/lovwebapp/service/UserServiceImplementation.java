package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Primary
@Service
public class UserServiceImplementation implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    private MailSenderService mailSenderService;

    public UserServiceImplementation(UserRepo userRepo, PasswordEncoder passwordEncoder, VerificationTokenService verificationTokenService, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.mailSenderService = mailSenderService;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findAllByUsername(s);
    }
}

