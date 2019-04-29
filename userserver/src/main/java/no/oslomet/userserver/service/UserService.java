package no.oslomet.userserver.service;

import no.oslomet.userserver.model.TweetUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<TweetUser> getAllUsers();
    TweetUser getUserByEmail(String email);
    TweetUser getUserById(Long id);
    TweetUser saveUser(TweetUser user);
    void deleteUserById(Long id);

}
