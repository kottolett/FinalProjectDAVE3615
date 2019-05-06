package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.Tweet;
import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.TweetService;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TweetService tweetService;


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweets", tweetService.getAllTweets());
        return "admin";
    }

    @PostMapping("/changeAdmin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeAdmin(@RequestParam String newPassword, @PathVariable Long id) {
        TweetUser us = userService.getUserById(id);
        us.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(us.getId(), us);
        return "redirect:/admin";
    }

    @GetMapping("/deleteAdmin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAdmin(@PathVariable Long id) {
        List<Tweet> tweets = tweetService.getAllByUserId(id);
        for (Tweet tweet : tweets) {
            tweetService.deleteTweetById(tweet.getId());
        }
        for (Tweet tweet : tweetService.getAllTweets()) {
            if (tweet.getRetweets().containsKey(id)) {
                HashMap<Long, LocalDateTime> retweet = tweet.getRetweets();
                retweet.remove(id);
                tweet.setRetweets(retweet);
                tweetService.updateTweet(tweet.getId(), tweet);
            }
        }
        userService.deleteUserById(id);
        return "redirect:/logout";
    }
}
