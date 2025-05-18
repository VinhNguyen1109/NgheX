package com.nghex.exe202.service;

import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.UserException;

public interface UserService {
	public User findUserProfileByJwt(String jwt) throws UserException;
	public User findUserByEmail(String email) throws UserException;


}
