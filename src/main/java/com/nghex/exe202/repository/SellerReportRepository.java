package com.nghex.exe202.repository;

import com.nghex.exe202.entity.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport,Long> {
    SellerReport findBySellerId(Long sellerId);
}

