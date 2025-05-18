package com.nghex.exe202.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	
	private String password;
	private String token;


}
