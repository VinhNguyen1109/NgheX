package com.nghex.exe202.service.impl;

import com.nghex.exe202.entity.Membership;
import com.nghex.exe202.repository.MemberShipRepository;
import com.nghex.exe202.service.MemberShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberShipServiceImpl implements MemberShipService {
    private final MemberShipRepository membershipRepository;
    @Override
    public void saveMemberShip(Membership membership) {
        membershipRepository.save(membership);
    }
}
