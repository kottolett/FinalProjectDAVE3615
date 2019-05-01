package no.oslomet.tweetserver.service;

import no.oslomet.tweetserver.model.TweetMap;
import no.oslomet.tweetserver.model.Tweet;
import no.oslomet.tweetserver.repository.TweetMapRepository;
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
    private TweetMapRepository tweetMapRepository;

    public List<Tweet> getAllTweets() { return tweetRepository.findAll(Sort.by(Sort.Direction.DESC, "postTime")); }

    public List<Tweet> getAllTweetsFromUser(Long userId) { return tweetRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "postTime")); }

    public List<Tweet> searchTweets(Specification<Tweet> search) { return tweetRepository.findAll(search, Sort.by(Sort.Direction.DESC, "postTime")); }

    public Tweet getTweetById(Long id) { return tweetRepository.findById(id).get(); }

    public List<TweetMap> getAllTweetsMapped() { return tweetMapRepository.findAll(); }

    public Tweet saveTweet(Tweet tweet) { return tweetRepository.save(tweet); }

    public TweetMap saveTweetMap(TweetMap tweetMap) { return tweetMapRepository.save(tweetMap); }

    public void deleteTweetById(Long id) { tweetRepository.deleteById(id); }
}
