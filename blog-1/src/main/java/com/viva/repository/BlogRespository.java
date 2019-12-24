package com.viva.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viva.model.UserInfo;
import com.viva.model.BlogModel;

@Repository
public interface BlogRespository  extends JpaRepository<BlogModel, Integer>{
	
	List<BlogModel> findAllByTitle(String text);
	List<BlogModel> findAllByTitleContaining(String pattern);
//	
	//@Query(value = "select * FROM blog_model where user_id=?1", nativeQuery = true)
	//List<BlogModel> getAllBlogsByUser(int userId);
	
	List<BlogModel> findAllByUser(UserInfo user);
	List<BlogModel> findAllByOrderByCreatedDateDesc();
	//List<BlogModel> 
}
