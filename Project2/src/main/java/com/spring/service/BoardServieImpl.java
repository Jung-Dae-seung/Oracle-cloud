package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.FreeBoardVO;
import com.spring.domain.InqAttachFileDTO;
import com.spring.domain.InqBoardVO;
import com.spring.domain.ProAttachFileDTO;
import com.spring.domain.ProBoardVO;
import com.spring.domain.Criteria;
import com.spring.mapper.FreeBoardAttachMapper;
import com.spring.mapper.FreeBoardMapper;
import com.spring.mapper.InqBoardAttachMapper;
import com.spring.mapper.InqBoardMapper;
import com.spring.mapper.ProBoardAttachMapper;
import com.spring.mapper.ProBoardMapper;

@Service
public class BoardServieImpl implements BoardService {

	@Autowired
	private FreeBoardMapper freeMapper;
	
	@Autowired
	private ProBoardMapper proMapper;
	
	@Autowired
	private InqBoardMapper inqMapper;
	
	@Autowired
	private FreeBoardAttachMapper freeattachMapper;
	
	@Autowired
	private ProBoardAttachMapper proattachMapper;
	
	@Autowired
	private InqBoardAttachMapper inqattachMapper;


	@Transactional
	@Override
	public boolean insert_f(FreeBoardVO vo) { //자유게시판
		//새글 등록		
		boolean result=freeMapper.insert(vo)>0?true:false;
			
		//첨부파일 등록
		if(vo.getAttachList()==null || vo.getAttachList().size()<=0) {
			return result;
		}		
		
		vo.getAttachList().forEach(attach ->{
			attach.setBno(vo.getBno());
			freeattachMapper.insert(attach);			
		});
		
		return result;
	}

	@Transactional
	@Override
	public boolean insert_p(ProBoardVO vo) { //홍보게시판
		//새글 등록		
		boolean result=proMapper.insert(vo)>0?true:false;
			
		//첨부파일 등록
		if(vo.getAttachList()==null || vo.getAttachList().size()<=0) {
			return result;
		}		
		
		vo.getAttachList().forEach(attach ->{
			attach.setBno(vo.getBno());
			proattachMapper.insert(attach);			
		});
		
		return result;
	}

	@Transactional
	@Override
	public boolean insert_i(InqBoardVO vo) { //문의게시판
		//새글 등록		
		boolean result=inqMapper.insert(vo)>0?true:false;
			
		//첨부파일 등록
		if(vo.getAttachList()==null || vo.getAttachList().size()<=0) {
			return result;
		}		
		
		vo.getAttachList().forEach(attach ->{
			attach.setBno(vo.getBno());
			inqattachMapper.insert(attach);			
		});
		
		return result;
	}

	@Transactional
	@Override
	public boolean delete_f(int bno) {
		//첨부파일 삭제
		freeattachMapper.delete(bno);
		//게시글 삭제
		return freeMapper.delete(bno)>0?true:false;
	}

	@Transactional
	@Override
	public boolean delete_p(int bno) {
		//첨부파일 삭제
		proattachMapper.delete(bno);
		//게시글 삭제
		return proMapper.delete(bno)>0?true:false;
	}

	@Transactional
	@Override
	public boolean delete_i(int bno) {
		//첨부파일 삭제
		inqattachMapper.delete(bno);
		//게시글 삭제
		return inqMapper.delete(bno)>0?true:false;
	}

	@Transactional
	@Override
	public boolean update_f(FreeBoardVO vo) {
		//기존에 첨부파일 정보 모두 삭제 후 삽입
		freeattachMapper.delete(vo.getBno());
		
		//게시글 수정
		boolean modifyResult = freeMapper.update(vo)>0?true:false;
		
		if(vo.getAttachList() == null) {
			return modifyResult;
		}
		
		//첨부파일 삽입
		if(modifyResult && vo.getAttachList().size()>0) {
			for(FreeAttachFileDTO dto:vo.getAttachList()) {
				dto.setBno(vo.getBno());
				freeattachMapper.insert(dto);
			}
		}
		return modifyResult;
	}

	@Transactional
	@Override
	public boolean update_p(ProBoardVO vo) {
		//기존에 첨부파일 정보 모두 삭제 후 삽입
		proattachMapper.delete(vo.getBno());
		
		//게시글 수정
		boolean modifyResult = proMapper.update(vo)>0?true:false;
		
		if(vo.getAttachList() == null) {
			return modifyResult;
		}
		
		//첨부파일 삽입
		if(modifyResult && vo.getAttachList().size()>0) {
			for(ProAttachFileDTO dto:vo.getAttachList()) {
				dto.setBno(vo.getBno());
				proattachMapper.insert(dto);
			}
		}
		return modifyResult;
	}

	@Transactional
	@Override
	public boolean update_i(InqBoardVO vo) {
		//기존에 첨부파일 정보 모두 삭제 후 삽입
		inqattachMapper.delete(vo.getBno());
		
		//게시글 수정
		boolean modifyResult = inqMapper.update(vo)>0?true:false;
		
		if(vo.getAttachList() == null) {
			return modifyResult;
		}
		
		//첨부파일 삽입
		if(modifyResult && vo.getAttachList().size()>0) {
			for(InqAttachFileDTO dto:vo.getAttachList()) {
				dto.setBno(vo.getBno());
				inqattachMapper.insert(dto);
			}
		}
		return modifyResult;
	}

	@Override
	public List<FreeBoardVO> list_f(Criteria cri) {
		return freeMapper.list(cri);
	}

	@Override
	public List<ProBoardVO> list_p(Criteria cri) {
		return proMapper.list(cri);
	}

	@Override
	public List<InqBoardVO> list_i(Criteria cri) {
		return inqMapper.list(cri);
	}

	@Override
	public FreeBoardVO read_f(int bno) {
		return freeMapper.read(bno);
	}

	@Override
	public ProBoardVO read_p(int bno) {
		return proMapper.read(bno);
	}

	@Override
	public InqBoardVO read_i(int bno) {
		return inqMapper.read(bno);
	}

	@Override
	public int total_f(Criteria cri) {
		return freeMapper.totalCnt(cri);
	}

	@Override
	public int total_p(Criteria cri) {
		return proMapper.totalCnt(cri);
	}

	@Override
	public int total_i(Criteria cri) {
		return inqMapper.totalCnt(cri);
	}
	
	@Override
	public List<FreeAttachFileDTO> getAttachList_f(int bno) {		
		return freeattachMapper.findByBno(bno);
	}
	
	@Override
	public List<ProAttachFileDTO> getAttachList_p(int bno) {		
		return proattachMapper.findByBno(bno);
	}
	
	@Override
	public List<InqAttachFileDTO> getAttachList_i(int bno) {		
		return inqattachMapper.findByBno(bno);
	}
}









