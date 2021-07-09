package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.domain.FreeBoardVO;
import com.spring.domain.Criteria;

public interface FreeBoardMapper {
	public int insert(FreeBoardVO vo);
	public int delete(int bno);
	public int update(FreeBoardVO vo);
	public List<FreeBoardVO> list(Criteria cri);
	public FreeBoardVO read(int bno);
	public int totalCnt(Criteria cri);
	public int replyCntUpdate(@Param("bno") int bno,@Param("amount") int amount);
}
