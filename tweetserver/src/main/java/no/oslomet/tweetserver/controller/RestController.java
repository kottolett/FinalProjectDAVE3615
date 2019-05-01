package no.oslomet.tweetserver.controller;

import com.google.common.base.Joiner;
import no.oslomet.tweetserver.model.TweetMap;
import no.oslomet.tweetserver.model.Tweet;
import no.oslomet.tweetserver.search.SearchOperation;
import no.oslomet.tweetserver.search.TweetSpecificationsBuilder;
import no.oslomet.tweetserver.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private TweetService tweetService;

    @GetMapping("/")
    public String home() { return "This is a REST controller"; }

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets() { return tweetService.getAllTweets(); }

    @GetMapping("/tweets/{id}")
    public Tweet getTweetById(@PathVariable Long id) { return tweetService.getTweetById(id); }

    @GetMapping("/tweets/user/{userId}")
    public List<Tweet> getAllByUserId(@PathVariable Long userId) { return tweetService.getAllTweetsFromUser(userId); }

    @RestResource(exported = false)
    @DeleteMapping("/tweets/{id}")
    public void deleteTweetById(@PathVariable Long id) { tweetService.deleteTweetById(id); }

    @PostMapping("/tweets")
    public Tweet saveTweet(@RequestBody Tweet newTweet) {
        return tweetService.saveTweet(newTweet);
    }

    @RestResource(exported = false)
    @PutMapping("/tweets/{id}")
    public Tweet updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        tweet.setId(id);
        return tweetService.saveTweet(tweet);
    }

    @GetMapping("/tweets/map")
    public List<TweetMap> getAllTweetsMapped() { return tweetService.getAllTweetsMapped(); }

    @PostMapping("/tweets/map")
    public TweetMap saveTweetMap(@RequestBody TweetMap tweetMap) { return tweetService.saveTweetMap(tweetMap); }

    @GetMapping("/tweets/search")
    @ResponseBody
    public List<Tweet> search(@RequestParam(value="q") String search) {
        TweetSpecificationsBuilder builder = new TweetSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }

        Specification<Tweet> spec = builder.build();
        System.out.println(spec);
        return tweetService.searchTweets(spec);
    }
}
