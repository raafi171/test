package com.viva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viva.model.UserInfo;

public interface AuthRepository extends JpaRepository<UserInfo , Integer>{
	UserInfo findByUserName(String userName);

}
