package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    @Query("delete from Cart c where c.id = :id")
    void deleteAllCartForUser(@Param("id") Long id);
}
