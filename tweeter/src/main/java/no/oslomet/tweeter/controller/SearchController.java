package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.TweetService;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @GetMapping("/searchTweet")
    public String searchTweet(String q, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.searchTweets("tweetContent:*"+q+"*"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/searchTag")
    public String searchTag(String q, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.searchTweets("tags:*"+q+"*"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/searchUser")
    public String searchUser(String q, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.searchTweets("userId:"+q));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

}
