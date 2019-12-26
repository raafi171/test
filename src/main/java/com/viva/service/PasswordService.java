package com.viva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public String encode(String password) {
		return encoder.encode(password);
	}
	
	public boolean matches(String password, String hash) {
		return encoder.matches(password, hash);
	}

}
