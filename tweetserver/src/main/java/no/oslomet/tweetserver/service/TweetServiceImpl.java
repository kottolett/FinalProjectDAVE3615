package no.oslomet.tweetserver.service;

import no.oslomet.tweetserver.model.Retweet;
import no.oslomet.tweetserver.model.Tweet;
import no.oslomet.tweetserver.repository.RetweetRepository;
import no.oslomet.tweetserver.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private RetweetRepository retweetRepository;

    public List<Tweet> getAllTweets() { return tweetRepository.findAll(Sort.by(Sort.Direction.DESC, "postTime")); }

    public List<Tweet> getAllTweetsFromUser(Long userId) { return tweetRepository.findAllByUserId(userId); }

    public List<Tweet> searchTweets(Specification<Tweet> search) { return tweetRepository.findAll(search, Sort.by(Sort.Direction.DESC, "postTime")); }

    public Tweet getTweetById(Long id) { return tweetRepository.findById(id).get(); }

    public List<Retweet> getAllRetweets() { return retweetRepository.findAll(); }

    public Tweet saveTweet(Tweet tweet) { return tweetRepository.save(tweet); }

    public Retweet saveRetweet(Retweet retweet) { return retweetRepository.save(retweet); }

    public void deleteTweetById(Long id) { tweetRepository.deleteById(id); }
}
