package com.viva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.viva.model.UserInfo;
import com.viva.repository.AuthRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private AuthRepository authRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInfo user = authRepository.findByUserName(username);	
		UserDetailsImpl details = new UserDetailsImpl();
		details.setUserName(user.getUserName());
		details.setPassword(user.getPassword());
		return details;
	}
}
