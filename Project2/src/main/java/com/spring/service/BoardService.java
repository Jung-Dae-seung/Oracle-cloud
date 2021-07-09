package com.spring.service;

import java.util.List;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.FreeBoardVO;
import com.spring.domain.InqAttachFileDTO;
import com.spring.domain.InqBoardVO;
import com.spring.domain.ProAttachFileDTO;
import com.spring.domain.ProBoardVO;
import com.spring.domain.Criteria;

public interface BoardService {

	public boolean insert_f(FreeBoardVO vo);
	public boolean insert_p(ProBoardVO vo);
	public boolean insert_i(InqBoardVO vo);
	
	public boolean delete_f(int bno);
	public boolean delete_p(int bno);
	public boolean delete_i(int bno);
	
	public boolean update_f(FreeBoardVO vo);
	public boolean update_p(ProBoardVO vo);
	public boolean update_i(InqBoardVO vo);
	
	public List<FreeBoardVO> list_f(Criteria cri);
	public List<ProBoardVO> list_p(Criteria cri);
	public List<InqBoardVO> list_i(Criteria cri);
	
	public FreeBoardVO read_f(int bno);
	public ProBoardVO read_p(int bno);
	public InqBoardVO read_i(int bno);
	
	public int total_f(Criteria cri);
	public int total_p(Criteria cri);
	public int total_i(Criteria cri);
	
	
	public List<FreeAttachFileDTO> getAttachList_f(int bno);
	public List<ProAttachFileDTO> getAttachList_p(int bno);
	public List<InqAttachFileDTO> getAttachList_i(int bno);
}
