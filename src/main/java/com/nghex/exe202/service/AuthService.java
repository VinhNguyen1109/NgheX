package com.nghex.exe202.service;


import com.nghex.exe202.exception.SellerException;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.request.LoginRequest;
import com.nghex.exe202.request.SignupRequest;
import com.nghex.exe202.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {
    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
