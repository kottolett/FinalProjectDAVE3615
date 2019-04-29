package no.oslomet.tweetserver.service;

import no.oslomet.tweetserver.model.Retweet;
import no.oslomet.tweetserver.model.Tweet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TweetService {

    List<Tweet> getAllTweets();
    List<Tweet> searchTweets(Specification<Tweet> search);
    List<Tweet> getAllTweetsFromUser(Long userId);
    Tweet getTweetById(Long id);
    Tweet saveTweet(Tweet tweet);
    void deleteTweetById(Long id);
    List<Retweet> getAllRetweets();
    Retweet saveRetweet(Retweet retweet);

}
