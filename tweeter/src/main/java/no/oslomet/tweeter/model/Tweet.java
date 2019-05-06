package no.oslomet.tweeter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class Tweet {
    private Long id;
    private LocalDateTime postTime;
    private String tweetContent;
    private String tags;
    private Long userId;
    private ArrayList<Long> likes;
    private HashMap<Long, LocalDateTime> retweets;
    private String media;
    private boolean rt;
    private LocalDateTime retweeted;

    public Tweet(LocalDateTime postTime, String tweetContent, String tags, Long userId, String media, ArrayList<Long> likes, HashMap<Long, LocalDateTime> retweets) {
        this.postTime = postTime;
        this.tweetContent = tweetContent;
        this.tags = tags;
        this.userId = userId;
        this.media = media;
        this.likes = likes;
        this.retweets = retweets;
    }
}
