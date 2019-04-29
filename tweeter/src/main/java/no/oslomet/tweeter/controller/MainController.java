package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.Tweet;
import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.TweetService;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    private Pattern hashtag = Pattern.compile("#\\w+");

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("tweets", tweetService.getAllTweets());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.getAllTweets());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping("/saveTweet")
    public String saveTweet(@ModelAttribute("tweet") Tweet tweet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        tweet.setUserId(user.getId());
        tweet.setPostTime(LocalDateTime.now().withNano(0));
        tweet.setLikes(0);
        tweet.setRetweets(0);

        StringBuilder builder = new StringBuilder();
        Matcher matcher = hashtag.matcher(tweet.getTweetContent());
        while (matcher.find()) {
            tweet.setTags(builder.append(matcher.group().replaceAll("#", "")).toString());
        }
        tweetService.saveTweet(tweet);
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("tweets", tweetService.getAllByUserId(user.getId()));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }



}
