package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.util.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
