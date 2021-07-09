package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.domain.FreeBoardVO;
import com.spring.domain.ProBoardVO;
import com.spring.domain.Criteria;

public interface ProBoardMapper {
	public int insert(ProBoardVO vo);
	public int delete(int bno);
	public int update(ProBoardVO vo);
	public List<ProBoardVO> list(Criteria cri);
	public ProBoardVO read(int bno);
	public int totalCnt(Criteria cri);
	public int replyCntUpdate(@Param("bno") int bno,@Param("amount") int amount);
}
