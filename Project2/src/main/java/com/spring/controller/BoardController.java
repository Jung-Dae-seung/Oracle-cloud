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
import com.spring.service.BoardService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/board/*")
public class BoardController {

	@Autowired
	private BoardService service;
	
	//자유게시판 - list
	@GetMapping("/free_board_list")
	public void list_f(Model model,Criteria cri) {
		log.info("전체 리스트 요청 ");
		
		//사용자가 선택한 페이지 게시물
		List<FreeBoardVO> list=service.list_f(cri);
		//전체 게시물 수 
		int total = service.total_f(cri);
				
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	
	//홍보게시판 - list
	@GetMapping("/pro_board_list")
	public void list_p(Model model,Criteria cri) {
		log.info("전체 리스트 요청 ");
		
		//사용자가 선택한 페이지 게시물
		List<ProBoardVO> list=service.list_p(cri);
		//전체 게시물 수 
		int total = service.total_f(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	
	//문의게시판 - list
	@GetMapping("/inq_board_list")
	public void list_i(Model model,Criteria cri) {
		log.info("전체 리스트 요청 ");
		
		//사용자가 선택한 페이지 게시물
		List<InqBoardVO> list=service.list_i(cri);
		//전체 게시물 수 
		int total = service.total_i(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	@GetMapping("/register")
	public void register() {
		log.info("새글 등록 폼 요청");
	}
	
	//자유게시판 - 게시글 등록
	@PostMapping("/register")
	public String registerPost_f(FreeBoardVO vo,RedirectAttributes rttr) {
		log.info("새글 등록 요청 "+vo);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_f(vo)) {
			//log.info("입력된 글 번호 "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:free_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	//홍보게시판 - 게시글 등록
	@PostMapping("/register")
	public String registerPost_p(ProBoardVO vo,RedirectAttributes rttr) {
		log.info("새글 등록 요청 "+vo);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_p(vo)) {
			//log.info("입력된 글 번호 "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:pro_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	//문의게시판 - 게시글 등록
	@PostMapping("/register")
	public String registerPost_i(InqBoardVO vo,RedirectAttributes rttr) {
		log.info("새글 등록 요청 "+vo);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_i(vo)) {
			//log.info("입력된 글 번호 "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:inq_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	
	//   /board/read?bno=22  
    //   /board/modify?bno=7
	
	
	//자유게시판 글 하나 읽기
	@GetMapping({"/read","/modify"})
	public void read_f(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("글 하나 가져오기 "+bno+" cri : "+cri);  
		
		FreeBoardVO vo=service.read_f(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	//홍보게시판 글 하나 읽기
	@GetMapping({"/read","/modify"})
	public void read_p(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("글 하나 가져오기 "+bno+" cri : "+cri);  
		
		ProBoardVO vo=service.read_p(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	//문의게시판 글 하나 읽기
	@GetMapping({"/read","/modify"})
	public void read_i(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("글 하나 가져오기 "+bno+" cri : "+cri);  
		
		InqBoardVO vo=service.read_i(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	
	// modify+post 수정한 후 list - 자유게시판
	@PostMapping("/modify")
	public String modify_f(FreeBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("수정 요청 "+vo+" 페이지 나누기 "+cri);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_f(vo);		
		
		
		rttr.addFlashAttribute("result","성공");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:free_board_list";
	}
	
	// modify+post 수정한 후 list - 홍보게시판
	@PostMapping("/modify")
	public String modify_p(ProBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("수정 요청 "+vo+" 페이지 나누기 "+cri);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_p(vo);		
		
		
		rttr.addFlashAttribute("result","성공");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:pro_board_list";
	}
	
	// modify+post 수정한 후 list - 문의게시판
	@PostMapping("/modify")
	public String modify_i(InqBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("수정 요청 "+vo+" 페이지 나누기 "+cri);
		
		//첨부 파일 확인
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_i(vo);		
		
		
		rttr.addFlashAttribute("result","성공");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:inq_board_list";
	}
	
	
	//게시글 삭제 + post - 자유게시판
	@PostMapping("/remove")
	public String remove_f(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("게시글 삭제 "+bno);
		
		
		//서버(폴더)에 저장된 첨부파일 삭제
		//① bno에 해당하는 첨부파일 목록 알아내기
		List<FreeAttachFileDTO> attachList=service.getAttachList_f(bno);
		
		//게시글 삭제 + 첨부파일 삭제
		if(service.delete_f(bno)) {
			//② 폴더 파일 삭제
			deleteFiles_f(attachList);
			rttr.addFlashAttribute("result","성공");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:free_board_list";
	}
	
	//게시글 삭제 + post - 홍보게시판
	@PostMapping("/remove")
	public String remove_p(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("게시글 삭제 "+bno);
		
		
		//서버(폴더)에 저장된 첨부파일 삭제
		//① bno에 해당하는 첨부파일 목록 알아내기
		List<ProAttachFileDTO> attachList=service.getAttachList_p(bno);
		
		//게시글 삭제 + 첨부파일 삭제
		if(service.delete_p(bno)) {
			//② 폴더 파일 삭제
			deleteFiles_p(attachList);
			rttr.addFlashAttribute("result","성공");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:pro_board_list";
	}
	
	//게시글 삭제 + post - 문의게시판
	@PostMapping("/remove")
	public String remove_i(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("게시글 삭제 "+bno);
		
		
		//서버(폴더)에 저장된 첨부파일 삭제
		//① bno에 해당하는 첨부파일 목록 알아내기
		List<InqAttachFileDTO> attachList=service.getAttachList_i(bno);
		
		//게시글 삭제 + 첨부파일 삭제
		if(service.delete_i(bno)) {
			//② 폴더 파일 삭제
			deleteFiles_i(attachList);
			rttr.addFlashAttribute("result","성공");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:inq_board_list";
	}
	
	
	
	//첨부물 가져오기 -f
	@GetMapping("/getAttachList")
	public ResponseEntity<List<FreeAttachFileDTO>> getAttachList_f(int bno){
		log.info("첨부물 가져오기 "+bno);
		
		return new ResponseEntity<List<FreeAttachFileDTO>>(service.getAttachList_f(bno),HttpStatus.OK);
	}
	
	//첨부물 가져오기 -p
	@GetMapping("/getAttachList")
	public ResponseEntity<List<ProAttachFileDTO>> getAttachList_p(int bno){
		log.info("첨부물 가져오기 "+bno);
		
		return new ResponseEntity<List<ProAttachFileDTO>>(service.getAttachList_p(bno),HttpStatus.OK);
	}
	
	//첨부물 가져오기 -q
	@GetMapping("/getAttachList")
	public ResponseEntity<List<InqAttachFileDTO>> getAttachList_i(int bno){
		log.info("첨부물 가져오기 "+bno);
		
		return new ResponseEntity<List<InqAttachFileDTO>>(service.getAttachList_i(bno),HttpStatus.OK);
	}
	
	
	//파일 삭제 -f
	private void deleteFiles_f(List<FreeAttachFileDTO> attachList) {
		log.info("첨부파일 삭제 "+attachList);
		
		if(attachList==null || attachList.size()<=0) {
			return;
		}
		
		for(FreeAttachFileDTO dto:attachList) {
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
	
	//파일 삭제 -p
	private void deleteFiles_p(List<ProAttachFileDTO> attachList) {
		log.info("첨부파일 삭제 "+attachList);
		
		if(attachList==null || attachList.size()<=0) {
			return;
		}
		
		for(ProAttachFileDTO dto:attachList) {
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
	
	//파일 삭제 -i
	private void deleteFiles_i(List<InqAttachFileDTO> attachList) {
		log.info("첨부파일 삭제 "+attachList);
		
		if(attachList==null || attachList.size()<=0) {
			return;
		}
		
		for(InqAttachFileDTO dto:attachList) {
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






















