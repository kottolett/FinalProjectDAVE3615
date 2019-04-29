package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistered(LocalDateTime.now().withNano(0));
        user.setRole("USER");
        userService.saveUser(user);
        return "redirect:/";
    }
}
