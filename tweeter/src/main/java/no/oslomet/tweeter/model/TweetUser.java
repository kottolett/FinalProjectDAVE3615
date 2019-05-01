package no.oslomet.tweeter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class TweetUser {
    private Long id;
    private String email;
    private String userName;
    private String password;
    private String role;
    private LocalDateTime registered;
    private String name;
    private String url;
    private ArrayList<Long> friends;

    public TweetUser(String email, String userName, String password, String role, LocalDateTime registered, String name, String url) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.registered = registered;
        this.name = name;
        this.url = url;

    }
}