package com.nghex.exe202.service.impl;

import com.nghex.exe202.entity.VerificationCode;
import com.nghex.exe202.repository.VerificationCodeRepository;
import com.nghex.exe202.service.VerificationService;
import org.springframework.stereotype.Service;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final VerificationCodeRepository verificationCodeRepository;

    VerificationServiceImpl(VerificationCodeRepository verificationCodeRepository){

        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    public VerificationCode createVerificationCode(String otp, String email) {
        VerificationCode isExist=verificationCodeRepository.findByEmail(email);

        if(isExist!=null) {
            verificationCodeRepository.delete(isExist);
        }

        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        return verificationCodeRepository.save(verificationCode);

    }
}
