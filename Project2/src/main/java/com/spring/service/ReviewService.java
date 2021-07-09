package com.spring.service;

import java.util.List;

import com.spring.domain.Criteria;
import com.spring.domain.ReviewAttachFileDTO;
import com.spring.domain.ReviewVO;
public interface ReviewService {

	public boolean insert(ReviewVO vo);
	public boolean delete(int bno);
	public boolean update(ReviewVO vo);
	public List<ReviewVO> list(Criteria cri);
	public ReviewVO read(int bno);
	public int total(Criteria cri);
	public List<ReviewAttachFileDTO> getAttachList(int bno);
	
	
}
