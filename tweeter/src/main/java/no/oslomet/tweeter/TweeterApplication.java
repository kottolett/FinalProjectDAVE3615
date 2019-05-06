package no.oslomet.tweeter;

import no.oslomet.tweeter.model.TweetUser;
import no.oslomet.tweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

@SpringBootApplication
public class TweeterApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TweeterApplication.class, args);
    }

    @Override
    public void run(String... args) {
        seedUsers();
    }

    public void seedUsers() {
        userService.saveUser(new TweetUser("kim@oslomet.no", "kim247", passwordEncoder.encode("admin"), "ADMIN", LocalDateTime.of(2019, Month.FEBRUARY, 10, 3, 55, 0), "2Kim4School", "", new ArrayList<>(), new ArrayList<>()));
        userService.saveUser(new TweetUser("ali@oslomet.no", "ali365", passwordEncoder.encode("test"), "USER", LocalDateTime.of(2019, Month.FEBRUARY, 22, 9, 38, 2), "Ali4prez", "https://oslomet.instructure.com/", new ArrayList<>(), new ArrayList<>()));
        userService.saveUser(new TweetUser("august@oslomet.no", "august420", passwordEncoder.encode("test"), "USER", LocalDateTime.of(2019, Month.APRIL, 2, 15, 22, 16), "AuGuSt", "", new ArrayList<>(), new ArrayList<>()));
        userService.saveUser(new TweetUser("dril@tweeter.no", "dril", passwordEncoder.encode("dril"), "USER", LocalDateTime.now(), "wint", "", new ArrayList<>(), new ArrayList<>()));
        userService.saveUser(new TweetUser("admin@oslomet.no", "admin", passwordEncoder.encode("admin"), "ADMIN", LocalDateTime.now(), "admin", "", new ArrayList<>(), new ArrayList<>()));
    }
}
