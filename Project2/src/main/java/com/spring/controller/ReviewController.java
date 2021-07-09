package com.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.FreeAttachFileDTO;
import com.spring.domain.FreeBoardVO;
import com.spring.domain.InqAttachFileDTO;
import com.spring.domain.InqBoardVO;
import com.spring.domain.Criteria;
import com.spring.domain.PageVO;
import com.spring.domain.ProAttachFileDTO;
import com.spring.domain.ProBoardVO;
import com.spring.domain.ReviewAttachFileDTO;
import com.spring.domain.ReviewVO;
import com.spring.service.BoardService;
import com.spring.service.ReviewService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/board/*")
public class ReviewController {

	@Autowired
	private ReviewService service;
	
	//리뷰 리스트
	@GetMapping("/")
	public void list(Model model,Criteria cri) {
		log.info("전체 리스트 요청 ");
		
		//사용자가 선택한 페이지 게시물
		List<ReviewVO> list=service.list(cri);
		//전체 게시물 수 
		int total = service.total(cri);
				
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	
	@GetMapping("/")
	public void register() {
		log.info("리뷰 등록 폼 요청");
	}
	
	//리뷰등록
	@PostMapping("/")
	public String registerPost(ReviewVO vo,RedirectAttributes rttr) {
		log.info("새글 등록 요청 "+vo);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert(vo)) {
			//log.info("입력된 글 번호 "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "";    //   redirect:/board/list
		}else {
			return ""; //  redirect:/board/register
		}
	}
	
	//리뷰읽기
	@GetMapping({"/read","/modify"})
	public void read(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("글 하나 가져오기 "+bno+" cri : "+cri);  
		
		ReviewVO vo=service.read(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	
	// modify+post 수정한 후 list -
	@PostMapping("/modify")
	public String modify(ReviewVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("수정 요청 "+vo+" 페이지 나누기 "+cri);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update(vo);		
		
		rttr.addFlashAttribute("result","성공");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "";
	}
	
	
	//게시글 삭제 + post -
	@PostMapping("/remove")
	public String remove(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("게시글 삭제 "+bno);
		
		//서버(폴더)에 저장된 첨부파일 삭제
		//① bno에 해당하는 첨부파일 목록 알아내기
		List<ReviewAttachFileDTO> attachList=service.getAttachList(bno);
		
		//게시글 삭제 + 첨부파일 삭제
		if(service.delete(bno)) {
			//② 폴더 파일 삭제
			deleteFiles(attachList);
			rttr.addFlashAttribute("result","성공");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:free_board_list";
	}
	
	//첨부물 가져오기 
	@GetMapping("/")
	public ResponseEntity<List<ReviewAttachFileDTO>> getAttachList(int bno){
		log.info("첨부물 가져오기 "+bno);
		
		return new ResponseEntity<List<ReviewAttachFileDTO>>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	//파일 삭제
	private void deleteFiles(List<ReviewAttachFileDTO> attachList) {
		log.info("첨부파일 삭제 "+attachList);
		
		if(attachList==null || attachList.size()<=0) {
			return;
		}
		
		for(ReviewAttachFileDTO dto:attachList) {
			Path path = Paths.get("e:\\upload\\", dto.getUploadPath()+"\\"+dto.getUuid()+"_"+dto.getFileName());
			
			try {
				Files.deleteIfExists(path);
				
				if(Files.probeContentType(path).startsWith("image")) {
					Path thumbnail = Paths.get("e:\\upload\\", 
							dto.getUploadPath()+"\\s_"+dto.getUuid()+"_"+dto.getFileName());
					Files.delete(thumbnail);
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
		}
	}
	
}

