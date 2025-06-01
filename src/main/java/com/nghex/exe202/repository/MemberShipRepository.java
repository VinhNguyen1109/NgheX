package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberShipRepository extends JpaRepository<Membership, Long> {

}
