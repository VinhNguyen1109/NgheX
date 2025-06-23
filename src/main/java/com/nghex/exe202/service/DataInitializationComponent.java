package com.nghex.exe202.service;

import com.nghex.exe202.entity.User;
import com.nghex.exe202.repository.UserRepository;
import com.nghex.exe202.util.enums.USER_ROLE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) {
        initializeAdminUsers();
    }

    private void initializeAdminUsers() {
        List<String> adminEmails = Arrays.asList(
                "vinhnguyen11092003@gmail.com",
                "anhnttha170133@fpt.edu.vn",
                "Huyenntkha163073@fpt.edu.vn",
                "Thuylpha170098@fpt.edu.vn",
                "nguyencongvinh69@gmail.com"
        );

        for (String email : adminEmails) {
            if (userRepository.findByEmail(email) == null) {
                User adminUser = new User();
                adminUser.setPassword(passwordEncoder.encode("vinh11092003"));
                adminUser.setFullName("Admin");
                adminUser.setEmail(email);
                adminUser.setRole(USER_ROLE.ROLE_ADMIN);

                userRepository.save(adminUser);
            }
        }
    }


}