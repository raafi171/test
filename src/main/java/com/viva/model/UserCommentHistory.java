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
public class UserCommentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(length = 3000)
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userinfo_id", nullable = false)
	private UserInfo userReact;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_model_id", nullable = false)
	private BlogModel  blogReact;
	
}
