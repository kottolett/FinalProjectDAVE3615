package no.oslomet.userserver.repository;

import no.oslomet.userserver.model.TweetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TweetUser, Long> {
}
