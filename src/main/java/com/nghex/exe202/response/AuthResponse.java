package com.nghex.exe202.response;

import com.nghex.exe202.util.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	private String jwt;
	private boolean status;
	private String message;
	private USER_ROLE role;
}
