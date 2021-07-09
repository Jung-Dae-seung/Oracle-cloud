package com.spring.mapper;

import java.util.List;

import com.spring.domain.FreeAttachFileDTO;

public interface FreeBoardAttachMapper {
	public int insert(FreeAttachFileDTO attach);
	public int delete(int bno);
	public List<FreeAttachFileDTO> findByBno(int bno); 
	public List<FreeAttachFileDTO> getOldFiles();
}
