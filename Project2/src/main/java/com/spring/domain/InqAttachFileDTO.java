package com.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Setter
@Getter
public class InqAttachFileDTO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	private int bno;
}
