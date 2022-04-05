package uz.pdp.newssite.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.newssite.entity.Role;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.entity.enums.Permissions;
import uz.pdp.newssite.repository.RoleRepository;
import uz.pdp.newssite.repository.UserRepository;
import uz.pdp.newssite.utils.Constants;

import java.util.Arrays;

import static uz.pdp.newssite.entity.enums.Permissions.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initialMode;


    @Override
    public void run(String... args) throws Exception {

        if (initialMode.equals("always")) {
            Role admin = roleRepository.save(new Role(
                    Constants.ADMIN,
                    Arrays.asList(Permissions.values()),
                    "Admin system"
            ));

            Role user = roleRepository.save(new Role(
                    Constants.USER,
                    Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT),
                    "User system"
            ));

            userRepository.save(new User(
                    "Admin",
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true
            ));

            userRepository.save(new User(
                    "User",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
            ));
        }
    }
}
