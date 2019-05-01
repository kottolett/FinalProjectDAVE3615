package no.oslomet.tweeter.service;

import no.oslomet.tweeter.model.TweetMap;
import no.oslomet.tweeter.model.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {
    String BASE_URL = "http://localhost:9091/tweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Tweet> getAllTweets() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect(Collectors.toList());
    }

    public List<Tweet> getAllByUserId(Long userId) {
        return Arrays.stream(restTemplate.getForObject(BASE_URL+"/user/"+userId, Tweet[].class)
        ).collect(Collectors.toList());
    }

    public List<TweetMap> getAllTweetsMapped() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL+"/map", TweetMap[].class)
        ).collect(Collectors.toList());
    }

    public Tweet getTweetById(Long id) {
        Tweet tweet = restTemplate.getForObject(BASE_URL+"/"+id, Tweet.class);
        return tweet;
    }

    public List<Tweet> searchTweets(String query) {
        return Arrays.stream(restTemplate.getForObject(BASE_URL+"/search?q="+query, Tweet[].class)
        ).collect(Collectors.toList());
    }

    public Tweet saveTweet(Tweet newTweet) { return restTemplate.postForObject(BASE_URL, newTweet, Tweet.class); }

    public TweetMap saveTweetMap(TweetMap tweetMap) { return restTemplate.postForObject(BASE_URL+"/map", tweetMap, TweetMap.class); }


    public void updateTweet(Long id, Tweet updatedTweet) { restTemplate.put(BASE_URL+"/"+id, updatedTweet); }

    public void deleteTweetById(Long id) { restTemplate.delete(BASE_URL+"/"+id); }

}
