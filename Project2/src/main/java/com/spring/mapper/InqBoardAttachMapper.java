package com.spring.mapper;

import java.util.List;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.InqAttachFileDTO;

public interface InqBoardAttachMapper {
	public int insert(InqAttachFileDTO attach);
	public int delete(int bno);
	public List<InqAttachFileDTO> findByBno(int bno); 
	public List<InqAttachFileDTO> getOldFiles();
}
