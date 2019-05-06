package no.oslomet.tweetserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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
