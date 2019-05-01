package no.oslomet.userserver.service;

import no.oslomet.userserver.model.TweetUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<TweetUser> getAllUsers();
    TweetUser getUserById(Long id);
    TweetUser saveUser(TweetUser user);
    void deleteUserById(Long id);
    TweetUser addFriend(Long id, Long friend);

}
