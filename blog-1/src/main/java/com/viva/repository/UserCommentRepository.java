package com.viva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.viva.model.UserCommentHistory;;
public interface UserCommentRepository extends JpaRepository<UserCommentHistory, Integer>{

}
