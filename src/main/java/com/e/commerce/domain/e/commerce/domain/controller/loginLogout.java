package com.e.commerce.domain.e.commerce.domain.controller;

import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class loginLogout {

    @Autowired
    userRepo repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/users/login")
    public String loginUser(@RequestBody User user) {

        User existingUser = repo.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {

            // Use encoder.matches to compare raw password with encrypted password
            if (existingUser.isEnable()) {
                if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                    // Successfully logged in
                    existingUser.setInvalid_attempt_count(0);
                    existingUser.setActive(true);
                    repo.save(existingUser);
                    return "Successfully logged in!";

                } else {
                    // Wrong password
                    if (existingUser.getInvalid_attempt_count() == 3) {
                        existingUser.setLocked(true);
                        repo.save(existingUser);
                        return "To many invalid attempt Account locked ";
                    } else {
                        int i = existingUser.getInvalid_attempt_count();
                        existingUser.setInvalid_attempt_count(i + 1);
                        repo.save(existingUser);
                        return "Incorrect password. Try again.";
                    }
                }
            }
            else {
                return "Account is currently disable, to continue please confirm the link sent to your Register Email id";
            }
        } else {
            return "The email provided does not exist!";
        }
    }


    @PostMapping("/users/logout")
    public String logoutUser(@RequestBody User user) {

        User existingUser = repo.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            // Use encoder.matches to compare raw password with encrypted password
            if (existingUser.isActive()) {
                if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                    existingUser.setActive(false);

                    // Successfully logged out
                    repo.save(existingUser);
                    return "Successfully logged out!";
                }
                else {
                    // Wrong password
                    return "Incorrect password. Try again.";
                }
            }
            else {
                return "Already logged out";
            }
        }
        else {
            return "The email provided does not exist!";
        }
    }
}
