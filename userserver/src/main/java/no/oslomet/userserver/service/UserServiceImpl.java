package no.oslomet.userserver.service;

import no.oslomet.userserver.model.TweetUser;
import no.oslomet.userserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<TweetUser> getAllUsers() { return userRepository.findAll(); }

    public TweetUser getUserById(Long id) { return userRepository.findById(id).get(); }

    public Optional<TweetUser> getUserByEmail(String email) { return userRepository.findUserByEmail(email); }

    public TweetUser saveUser(TweetUser user) { return userRepository.save(user); }

    public void deleteUserById(Long id) { userRepository.deleteById(id); }

}
