package no.oslomet.userserver.repository;

import no.oslomet.userserver.model.TweetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TweetUser, Long> {

    Optional<TweetUser> findUserByEmail(String email);

}
