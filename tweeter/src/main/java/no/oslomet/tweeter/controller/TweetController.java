package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.Tweet;
import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.TweetService;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class TweetController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    private Pattern hashtag = Pattern.compile("#\\w+");

    @PostMapping("/saveTweet")
    public String saveTweet(@ModelAttribute("tweet") Tweet tweet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        tweet.setUserId(user.getId());
        tweet.setPostTime(LocalDateTime.now().withNano(0));
        tweet.setLikes(new ArrayList<>());
        tweet.setRetweets(new HashMap<>());
        if (tweet.getMedia().equals("")) {
            tweet.setMedia(null);
        }

        StringJoiner joiner = new StringJoiner(" ");
        Matcher matcher = hashtag.matcher(tweet.getTweetContent());
        while (matcher.find()) {
            tweet.setTags(joiner.add(matcher.group().replaceAll("#", "")).toString());
        }
        tweetService.saveTweet(tweet);
        return "redirect:/home";
    }

    @GetMapping("/deleteTweet/{id}")
    public String deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweetById(id);
        return "redirect:/home";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/undoRetweet/{id}")
    public String undoRetweet(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        Tweet tweet = tweetService.getTweetById(id);
        HashMap<Long, LocalDateTime> retweet = tweet.getRetweets();
        System.out.println(retweet);
        retweet.remove(user.getId());
        System.out.println(retweet);
        tweet.setRetweets(retweet);
        tweetService.updateTweet(id, tweet);
        return "redirect:/home";
    }

    @GetMapping("/likeTweet/{id}")
    public String likeTweet(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        Tweet liked = tweetService.getTweetById(id);
        ArrayList<Long> likes = liked.getLikes();
        if (likes.contains(user.getId())) {
            likes.remove(user.getId());
            liked.setLikes(likes);
            tweetService.updateTweet(liked.getId(), liked);
            return "redirect:/home";
        }
        likes.add(user.getId());
        liked.setLikes(likes);
        tweetService.updateTweet(liked.getId(), liked);
        return "redirect:/home";
    }

    @GetMapping("/retweet/{id}")
    public String retweet(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        Tweet tweet = tweetService.getTweetById(id);
        HashMap<Long, LocalDateTime> retweets = tweet.getRetweets();
        if (retweets.containsKey(user.getId())) {
            return "redirect:/home";
        }
        retweets.put(user.getId(), LocalDateTime.now());
        tweet.setRetweets(retweets);
        tweetService.updateTweet(tweet.getId(), tweet);
        return "redirect:/home";
    }
}
