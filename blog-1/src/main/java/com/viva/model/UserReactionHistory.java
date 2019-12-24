package com.viva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable= false)
	private int liked_Post;
	@Column(nullable = false)
	private int disliked_Post;
	@Column(nullable= false)
	private int user_id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_model_id", nullable = false)
	private BlogModel  blogPostReact;
	
}
