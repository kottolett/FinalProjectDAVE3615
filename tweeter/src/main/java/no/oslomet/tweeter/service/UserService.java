package no.oslomet.tweeter.service;

import no.oslomet.tweeter.model.TweetUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    String BASE_URL = "http://localhost:9090/users";
    private RestTemplate restTemplate = new RestTemplate();

    public List<TweetUser> getAllUsers() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL, TweetUser[].class)
        ).collect(Collectors.toList());
    }

    public TweetUser getUserById(Long id) {
        TweetUser user = restTemplate.getForObject(BASE_URL+"/"+id, TweetUser.class);
        return user;
    }

    public Optional<TweetUser> findUserByEmail(String email) {
        for (TweetUser user : getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return null;
    }

    public TweetUser saveUser(TweetUser newUser) { return restTemplate.postForObject(BASE_URL, newUser, TweetUser.class); }

    public void updateUser(Long id, TweetUser updatedUser) { restTemplate.put(BASE_URL+"/"+id, updatedUser); }

    public void deleteUserById(Long id) { restTemplate.delete(BASE_URL+"/"+id); }

}
