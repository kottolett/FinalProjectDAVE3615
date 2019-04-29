package no.oslomet.tweeter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Retweet {

    private Long tweetId;
    private Long userId;

    public Retweet(Long tweetId, Long userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }

}
