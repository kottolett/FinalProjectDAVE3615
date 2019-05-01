package no.oslomet.userserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
public class TweetUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void addFriend(Long id) {
        this.friends.add(id);
    }
}
