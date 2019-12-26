package com.viva.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HomePageDto {
	Date createdDate;
	String title;
	String userName;
	int id;
	int userID;
}
