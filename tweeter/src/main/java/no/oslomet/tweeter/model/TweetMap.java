package no.oslomet.tweeter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetMap {

    private Long tweetId;
    private Long userId;

    public TweetMap(Long tweetId, Long userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }

}
