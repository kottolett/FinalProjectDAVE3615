package no.oslomet.tweetserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
