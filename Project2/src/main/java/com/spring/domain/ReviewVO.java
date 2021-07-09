package com.spring.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVO {

	private String reviewer;
	private int bno;
	private String review;
	private int star;
	private Date reviewDate;
	private Date updatedate;
	private String restaurent;
	
	private List<ReviewAttachFileDTO> attachList;
}
