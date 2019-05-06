package no.oslomet.tweeter.controller;

import no.oslomet.tweeter.model.Tweet;
import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.TweetService;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @GetMapping("/follow/{id}")
    public String follow(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        TweetUser friend = userService.getUserById(id);

        ArrayList<Long> friends = user.getFollowing();
        friends.add(id);
        user.setFollowing(friends);

        ArrayList<Long> followers = friend.getFollowers();
        followers.add(user.getId());
        friend.setFollowers(followers);

        userService.updateUser(id, friend);
        userService.updateUser(user.getId(), user);
        return "redirect:/home";
    }

    @GetMapping("/unfollow/{id}")
    public String unfollow(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TweetUser user = userService.findUserByEmail(auth.getName()).get();
        TweetUser friend = userService.getUserById(id);

        ArrayList<Long> friends = user.getFollowing();
        friends.remove(id);
        user.setFollowing(friends);

        ArrayList<Long> followers = friend.getFollowers();
        followers.remove(user.getId());
        friend.setFollowers(followers);

        userService.updateUser(id, friend);
        userService.updateUser(user.getId(), user);
        return "redirect:/home";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
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
