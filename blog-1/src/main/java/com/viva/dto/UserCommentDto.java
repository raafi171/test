package com.viva.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDto {
	
	String comment;
	int id;
	int user_info_id;
	int blog_model_id;
}

