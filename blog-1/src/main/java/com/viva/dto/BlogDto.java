package com.viva.dto;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
	String title;
	String content;
	int id;
	int user_id;
	Date createddate;
	Date modifieddate;
	int totalPostLike;
	int totalPostDislike;
}
