package no.oslomet.tweeter.service;

import no.oslomet.tweeter.model.TweetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<TweetUser> user = userService.findUserByEmail(email);
        if(!user.isPresent()) throw new UsernameNotFoundException("Not found user with email: " + email);
        return getUserDetails(user.get());
    }

    private UserDetails getUserDetails(TweetUser user){
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}