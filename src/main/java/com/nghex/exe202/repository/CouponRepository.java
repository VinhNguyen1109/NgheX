package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	Coupon findByCode(String couponCode);

}
