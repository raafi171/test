package com.viva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viva.model.BlogModel;
import com.viva.model.UserReactionHistory;

public interface PostReactionRepository extends JpaRepository<UserReactionHistory, Integer> {
	//UserReactionHistory findByBlogId();

}
