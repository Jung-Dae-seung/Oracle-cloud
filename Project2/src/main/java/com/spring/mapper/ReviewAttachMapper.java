package com.spring.mapper;

import java.util.List;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.ReviewAttachFileDTO;

public interface ReviewAttachMapper {
	public int insert(ReviewAttachFileDTO attach);
	public int delete(int bno);
	public List<ReviewAttachFileDTO> findByBno(int bno); 
	public List<ReviewAttachFileDTO> getOldFiles();
}
