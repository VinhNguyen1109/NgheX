package com.nghex.exe202.service;

import com.nghex.exe202.entity.VerificationCode;

public interface VerificationService {
    VerificationCode createVerificationCode(String otp, String email);
}
