package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.domain.FreeBoardVO;
import com.spring.domain.InqBoardVO;
import com.spring.domain.Criteria;

public interface InqBoardMapper {
	public int insert(InqBoardVO vo);
	public int delete(int bno);
	public int update(InqBoardVO vo);
	public List<InqBoardVO> list(Criteria cri);
	public InqBoardVO read(int bno);
	public int totalCnt(Criteria cri);
	public int replyCntUpdate(@Param("bno") int bno,@Param("amount") int amount);
}
