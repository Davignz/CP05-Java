package br.fiap.cp5.securitymvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (users.findByUsername("admin") == null) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                u.setRole("ADMIN");
                u.setEnabled(true);
                users.save(u);
            }
        };
    }
}
