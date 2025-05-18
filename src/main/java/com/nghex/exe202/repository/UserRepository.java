package com.nghex.exe202.repository;

import com.nghex.exe202.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String username);
}
