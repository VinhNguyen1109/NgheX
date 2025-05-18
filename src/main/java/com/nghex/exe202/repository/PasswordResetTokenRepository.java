package com.nghex.exe202.repository;

import com.nghex.exe202.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
}
