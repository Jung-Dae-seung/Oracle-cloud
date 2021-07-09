package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.domain.Criteria;
import com.spring.domain.ReviewAttachFileDTO;
import com.spring.domain.ReviewVO;
import com.spring.mapper.ReviewAttachMapper;
import com.spring.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper mapper;
	
	@Autowired
	private ReviewAttachMapper attachMapper;
	
	@Transactional
	@Override
	public boolean insert(ReviewVO vo) {
		//���� ���		
		boolean result=mapper.insert(vo)>0?true:false;
			
		//÷������ ���
		if(vo.getAttachList()==null || vo.getAttachList().size()<=0) {
			return result;
		}		
		
		vo.getAttachList().forEach(attach ->{
			attach.setBno(vo.getBno());
			attachMapper.insert(attach);
		});
		
		return result;
	}

	@Transactional
	@Override
	public boolean delete(int bno) {
		//÷������ ����
		attachMapper.delete(bno);
		//�Խñ� ����
		return mapper.delete(bno)>0?true:false;
	}

	@Transactional
	@Override
	public boolean update(ReviewVO vo) {
		//������ ÷������ ���� ��� ���� �� ����
		attachMapper.delete(vo.getBno());
				
		//�Խñ� ����
		boolean modifyResult = mapper.update(vo)>0?true:false;
				
		if(vo.getAttachList() == null) {
			return modifyResult;
		}
				
		//÷������ ����
		if(modifyResult && vo.getAttachList().size()>0) {
			for(ReviewAttachFileDTO dto:vo.getAttachList()) {
				dto.setBno(vo.getBno());
				attachMapper.insert(dto);
			}
		}
		return modifyResult;
	}

	@Override
	public List<ReviewVO> list(Criteria cri) {
		return mapper.list(cri);
	}

	@Override
	public ReviewVO read(int bno) {
		return mapper.read(bno);
	}

	@Override
	public int total(Criteria cri) {
		return mapper.totalCnt(cri);
	}

	@Override
	public List<ReviewAttachFileDTO> getAttachList(int bno) {
		return attachMapper.findByBno(bno);
	}

}
