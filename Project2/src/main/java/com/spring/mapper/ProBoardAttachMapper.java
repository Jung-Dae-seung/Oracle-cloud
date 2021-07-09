package com.spring.mapper;

import java.util.List;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.ProAttachFileDTO;

public interface ProBoardAttachMapper {
	public int insert(ProAttachFileDTO attach);
	public int delete(int bno);
	public List<ProAttachFileDTO> findByBno(int bno); 
	public List<ProAttachFileDTO> getOldFiles();
}
