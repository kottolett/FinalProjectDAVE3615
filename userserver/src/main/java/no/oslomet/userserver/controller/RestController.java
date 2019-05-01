package no.oslomet.userserver.controller;

import no.oslomet.userserver.model.TweetUser;
import no.oslomet.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() { return "This is a REST controller"; }

    @GetMapping("/users")
    public List<TweetUser> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/users/{id}")
    public TweetUser getUserById(@PathVariable Long id) { return userService.getUserById(id); }

    @RestResource(exported = false)
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) { userService.deleteUserById(id); }

    @PostMapping("/users")
    public TweetUser saveUser(@RequestBody TweetUser newUser) { return userService.saveUser(newUser); }

    //@RestResource(exported = false)
    @PutMapping("/users/{id}")
    public TweetUser updateUser(@PathVariable Long id, @RequestBody TweetUser updatedUser) {
        updatedUser.setId(id);
        return userService.saveUser(updatedUser);
    }

    @PatchMapping("/users/{id}")
    public TweetUser addFriend(@PathVariable Long id, @RequestParam Long friend) {
        return userService.addFriend(id, friend);
    }
}
