package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") TweetUser user) {
        if (userService.emailExists(user.getEmail())) {
            throw new RuntimeException("Account already exists with this email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistered(LocalDateTime.now().withNano(0));
        user.setRole("USER");
        user.setFollowing(new ArrayList<>());
        user.setFollowers(new ArrayList<>());
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/change")
    public String change(@RequestParam String newPassword, @RequestParam String oldPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("New password same as old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user.getId(), user);
        return "redirect:/home";
    }
}
