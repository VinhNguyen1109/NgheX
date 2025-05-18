package com.nghex.exe202.service;

import com.nghex.exe202.entity.User;
import com.nghex.exe202.repository.UserRepository;
import com.nghex.exe202.util.enums.USER_ROLE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminUsername = "gladiator0195rr@gmail.com";

        if (userRepository.findByEmail(adminUsername)==null) {
            User adminUser = new User();

            adminUser.setPassword(passwordEncoder.encode("Prasad@321i"));
            adminUser.setFullName("Admin");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(USER_ROLE.ROLE_ADMIN);

            User admin=userRepository.save(adminUser);
        }
    }

}