package com.viva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReactionDto {
	int id;
	int liked_Post;
	int disliked_Post;
	int user_info_id;
	int blog_model_id;
}
