package no.oslomet.tweetserver.repository;

import no.oslomet.tweetserver.model.TweetMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetMapRepository extends JpaRepository<TweetMap, Long> {
}
