package no.oslomet.tweetserver;

import no.oslomet.tweetserver.model.Retweet;
import no.oslomet.tweetserver.model.Tweet;
import no.oslomet.tweetserver.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootApplication
public class TweetserverApplication implements CommandLineRunner {

    @Autowired
    private TweetService tweetService;

    public static void main(String[] args) {
        SpringApplication.run(TweetserverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.JANUARY, 15, 20, 23, 07), "dogs used to say \"Arf\" and \"Bow-Wow\" . Now they say shit like \"Wouwouwouwou\"", null, 4L, 0, 0, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.APRIL, 12, 23, 52, 10), "Dette er en test av #tags", "tags", 1L, 59, 0, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.APRIL, 3, 13, 23, 48), "swinging my great grandfather's nine iron around on my motorcycle and saying \"Seniors Rule\" every time i hit something", null, 4L, 7, 0, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.APRIL, 12, 23, 53, 38), "Dette er andre runde av #tags som testes", "tags", 1L, 21, 0, "https://i.kym-cdn.com/photos/images/original/001/474/406/e98.jpg"));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.FEBRUARY, 2, 15, 22, 16), null, null, 2L, 50, 0, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.JANUARY, 28, 21, 37, 8), "Dette er en såkalt test-tweet", null, 1L, 2, 0, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.MARCH, 12, 4, 8, 54), "Oh no!", null, 3L, 24354, 230, "https://i.kym-cdn.com/entries/icons/original/000/020/401/HereDatBoi.jpg"));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.FEBRUARY, 26, 12, 7, 23), "Dette er en bilde-test", null, 1L, 700, 52,  "https://i.kym-cdn.com/photos/images/original/001/486/497/cf3.jpg"));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.FEBRUARY, 22, 9, 38, 2), "Hey what is up guys, welcome back to my YouTube channel, #SMASH #that #like", "SMASH that like", 2L, 55, 4, null));
        tweetService.saveTweet(new Tweet(LocalDateTime.of(2019, Month.FEBRUARY, 10, 3, 55, 0), "move every game stop to the peak of K2 and you will find out damn quick who are the real gamers", null, 4L, 20, 0, null));
        tweetService.saveRetweet(new Retweet(1L, 3L));
    }
}
