package no.oslomet.tweetserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class TweetMap {

    @Id
    private Long tweetId;
    private Long userId;

    public TweetMap(Long tweetId, Long userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }

}
