package com.viva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viva.dto.AuthDto;
import com.viva.model.UserInfo;
import com.viva.repository.AuthRepository;

import ma.glasnost.orika.MapperFactory;

@Service
public class UserService {
	
	@Autowired
	AuthRepository userRepository;
	
	@Autowired
	MapperFactory mapperFactory;
	
	public AuthDto getUserByUserName(String userName) {
		UserInfo model = userRepository.findByUserName(userName);
		return AuthDto.builder()
				.id(model.getId())
				.userName(model.getUserName())
				.build();
		
	}

}
