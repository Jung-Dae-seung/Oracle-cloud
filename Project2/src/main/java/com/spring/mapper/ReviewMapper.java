package com.spring.mapper;

import java.util.List;

import com.spring.domain.Criteria;
import com.spring.domain.ReviewVO;

public interface ReviewMapper {
	public int insert(ReviewVO vo);
	public int delete(int bno);
	public int update(ReviewVO vo);
	public List<ReviewVO> list(Criteria cri);
	public ReviewVO read(int bno);
	public int totalCnt(Criteria cri);
}
