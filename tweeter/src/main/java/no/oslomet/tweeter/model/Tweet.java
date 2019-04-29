package no.oslomet.tweeter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Tweet {
    private Long id;
    private LocalDateTime postTime;
    private String tweetContent;
    private String tags;
    private Long userId;
    private Integer likes;
    private Integer retweets;
    private String media;

    public Tweet(LocalDateTime postTime, String tweetContent, String tags, Long userId, Integer likes, Integer retweets, String media) {
        this.postTime = postTime;
        this.tweetContent = tweetContent;
        this.tags = tags;
        this.userId = userId;
        this.likes = likes;
        this.retweets = retweets;
        this.media = media;
    }
}
