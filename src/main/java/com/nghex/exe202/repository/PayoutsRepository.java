package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Payouts;
import com.nghex.exe202.util.enums.PayoutsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayoutsRepository extends JpaRepository<Payouts,Long> {
    List<Payouts> findPayoutsBySellerId(Long sellerId);
    List<Payouts> findAllByStatus(PayoutsStatus status);
}

