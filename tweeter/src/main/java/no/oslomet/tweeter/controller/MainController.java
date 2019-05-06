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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @SuppressWarnings("Duplicates")
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Tweet> tweets = new ArrayList<>();
        for (Tweet tweet : tweetService.getAllTweets()) {
            if (!tweet.getRetweets().isEmpty()) {
                for (HashMap.Entry<Long, LocalDateTime> retweet : tweet.getRetweets().entrySet()) {
                    Tweet newTweet = new Tweet(tweet.getPostTime(), tweet.getTweetContent(), tweet.getTags(), tweet.getUserId(), tweet.getMedia(), tweet.getLikes(), tweet.getRetweets());
                    newTweet.setRt(true);
                    newTweet.setId(tweet.getId());
                    newTweet.setRetweeted(newTweet.getPostTime());
                    newTweet.setPostTime(retweet.getValue());
                    tweets.add(newTweet);
                }
                tweet.setRt(false);
                tweets.add(tweet);
            } else {
                tweet.setRt(false);
                tweets.add(tweet);
            }
        }
        Collections.sort(tweets, Comparator.comparing(Tweet::getPostTime).reversed());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweets", tweets);
        return "index";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        List<Tweet> tweets = new ArrayList<>();
        for (Tweet tweet : tweetService.getAllTweets()) {
            if (!tweet.getRetweets().isEmpty()) {
                for (HashMap.Entry<Long, LocalDateTime> retweet : tweet.getRetweets().entrySet()) {
                    Tweet newTweet = new Tweet(tweet.getPostTime(), tweet.getTweetContent(), tweet.getTags(), tweet.getUserId(), tweet.getMedia(), tweet.getLikes(), tweet.getRetweets());
                    newTweet.setRt(true);
                    newTweet.setId(tweet.getId());
                    newTweet.setRetweeted(newTweet.getPostTime());
                    newTweet.setPostTime(retweet.getValue());
                    tweets.add(newTweet);
                }
                tweet.setRt(false);
                tweets.add(tweet);
            } else {
                tweet.setRt(false);
                tweets.add(tweet);
            }
        }
        Collections.sort(tweets, Comparator.comparing(Tweet::getPostTime).reversed());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweets", tweets);
        return "index";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/profile")
    public String profile(Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        TweetUser selectedUser = userService.getUserById(id);
        model.addAttribute("selectedUser", selectedUser);
        List<Tweet> tweets = new ArrayList<>();
        for (Tweet tweet : tweetService.getAllByUserId(selectedUser.getId())) {
            if (!tweet.getRetweets().isEmpty()) {
                for (HashMap.Entry<Long, LocalDateTime> retweet : tweet.getRetweets().entrySet()) {
                    Tweet newTweet = new Tweet(tweet.getPostTime(), tweet.getTweetContent(), tweet.getTags(), tweet.getUserId(), tweet.getMedia(), tweet.getLikes(), tweet.getRetweets());
                    newTweet.setRt(true);
                    newTweet.setId(tweet.getId());
                    newTweet.setRetweeted(newTweet.getPostTime());
                    newTweet.setPostTime(retweet.getValue());
                    tweets.add(newTweet);
                }
                tweet.setRt(false);
                tweets.add(tweet);
            } else {
                tweet.setRt(false);
                tweets.add(tweet);
            }
        }
        Collections.sort(tweets, Comparator.comparing(Tweet::getPostTime).reversed());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweets", tweets);
        return "profile";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/following")
    public String following(Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());

        TweetUser selectedUser = userService.getUserById(id);
        model.addAttribute("selectedUser", selectedUser);

        List<Tweet> tweets = new ArrayList<>();
        for (Long f : selectedUser.getFollowing()) {
            tweets.addAll(tweetService.getAllByUserId(f));
        }
        Collections.sort(tweets, Comparator.comparing(Tweet::getPostTime).reversed());
        model.addAttribute("tweets", tweets);
        return "index";
    }
}
