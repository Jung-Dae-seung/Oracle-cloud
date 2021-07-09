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
	
	//�����Խ��� - list
	@GetMapping("/free_board_list")
	public void list_f(Model model,Criteria cri) {
		log.info("��ü ����Ʈ ��û ");
		
		//����ڰ� ������ ������ �Խù�
		List<FreeBoardVO> list=service.list_f(cri);
		//��ü �Խù� �� 
		int total = service.total_f(cri);
				
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	
	//ȫ���Խ��� - list
	@GetMapping("/pro_board_list")
	public void list_p(Model model,Criteria cri) {
		log.info("��ü ����Ʈ ��û ");
		
		//����ڰ� ������ ������ �Խù�
		List<ProBoardVO> list=service.list_p(cri);
		//��ü �Խù� �� 
		int total = service.total_f(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	
	//���ǰԽ��� - list
	@GetMapping("/inq_board_list")
	public void list_i(Model model,Criteria cri) {
		log.info("��ü ����Ʈ ��û ");
		
		//����ڰ� ������ ������ �Խù�
		List<InqBoardVO> list=service.list_i(cri);
		//��ü �Խù� �� 
		int total = service.total_i(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVO", new PageVO(cri, total));
	}	
	
	@GetMapping("/register")
	public void register() {
		log.info("���� ��� �� ��û");
	}
	
	//�����Խ��� - �Խñ� ���
	@PostMapping("/register")
	public String registerPost_f(FreeBoardVO vo,RedirectAttributes rttr) {
		log.info("���� ��� ��û "+vo);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_f(vo)) {
			//log.info("�Էµ� �� ��ȣ "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:free_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	//ȫ���Խ��� - �Խñ� ���
	@PostMapping("/register")
	public String registerPost_p(ProBoardVO vo,RedirectAttributes rttr) {
		log.info("���� ��� ��û "+vo);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_p(vo)) {
			//log.info("�Էµ� �� ��ȣ "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:pro_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	//���ǰԽ��� - �Խñ� ���
	@PostMapping("/register")
	public String registerPost_i(InqBoardVO vo,RedirectAttributes rttr) {
		log.info("���� ��� ��û "+vo);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		
		if(service.insert_i(vo)) {
			//log.info("�Էµ� �� ��ȣ "+vo.getBno());
			rttr.addFlashAttribute("result", vo.getBno());
			return "redirect:inq_board_list";    //   redirect:/board/list
		}else {
			return "redirect:register"; //  redirect:/board/register
		}
	}
	
	
	//   /board/read?bno=22  
    //   /board/modify?bno=7
	
	
	//�����Խ��� �� �ϳ� �б�
	@GetMapping({"/read","/modify"})
	public void read_f(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("�� �ϳ� �������� "+bno+" cri : "+cri);  
		
		FreeBoardVO vo=service.read_f(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	//ȫ���Խ��� �� �ϳ� �б�
	@GetMapping({"/read","/modify"})
	public void read_p(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("�� �ϳ� �������� "+bno+" cri : "+cri);  
		
		ProBoardVO vo=service.read_p(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	//���ǰԽ��� �� �ϳ� �б�
	@GetMapping({"/read","/modify"})
	public void read_i(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("�� �ϳ� �������� "+bno+" cri : "+cri);  
		
		InqBoardVO vo=service.read_i(bno);
		model.addAttribute("vo", vo);	//	/board/read  or  /board/modify 
	}
	
	
	// modify+post ������ �� list - �����Խ���
	@PostMapping("/modify")
	public String modify_f(FreeBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("���� ��û "+vo+" ������ ������ "+cri);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_f(vo);		
		
		
		rttr.addFlashAttribute("result","����");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:free_board_list";
	}
	
	// modify+post ������ �� list - ȫ���Խ���
	@PostMapping("/modify")
	public String modify_p(ProBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("���� ��û "+vo+" ������ ������ "+cri);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_p(vo);		
		
		
		rttr.addFlashAttribute("result","����");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:pro_board_list";
	}
	
	// modify+post ������ �� list - ���ǰԽ���
	@PostMapping("/modify")
	public String modify_i(InqBoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("���� ��û "+vo+" ������ ������ "+cri);
		
		//÷�� ���� Ȯ��
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(""+attach));
		}	
		
		service.update_i(vo);		
		
		
		rttr.addFlashAttribute("result","����");
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		
		return "redirect:inq_board_list";
	}
	
	
	//�Խñ� ���� + post - �����Խ���
	@PostMapping("/remove")
	public String remove_f(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("�Խñ� ���� "+bno);
		
		
		//����(����)�� ����� ÷������ ����
		//�� bno�� �ش��ϴ� ÷������ ��� �˾Ƴ���
		List<FreeAttachFileDTO> attachList=service.getAttachList_f(bno);
		
		//�Խñ� ���� + ÷������ ����
		if(service.delete_f(bno)) {
			//�� ���� ���� ����
			deleteFiles_f(attachList);
			rttr.addFlashAttribute("result","����");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:free_board_list";
	}
	
	//�Խñ� ���� + post - ȫ���Խ���
	@PostMapping("/remove")
	public String remove_p(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("�Խñ� ���� "+bno);
		
		
		//����(����)�� ����� ÷������ ����
		//�� bno�� �ش��ϴ� ÷������ ��� �˾Ƴ���
		List<ProAttachFileDTO> attachList=service.getAttachList_p(bno);
		
		//�Խñ� ���� + ÷������ ����
		if(service.delete_p(bno)) {
			//�� ���� ���� ����
			deleteFiles_p(attachList);
			rttr.addFlashAttribute("result","����");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:pro_board_list";
	}
	
	//�Խñ� ���� + post - ���ǰԽ���
	@PostMapping("/remove")
	public String remove_i(int bno,Criteria cri,RedirectAttributes rttr) {
		log.info("�Խñ� ���� "+bno);
		
		
		//����(����)�� ����� ÷������ ����
		//�� bno�� �ش��ϴ� ÷������ ��� �˾Ƴ���
		List<InqAttachFileDTO> attachList=service.getAttachList_i(bno);
		
		//�Խñ� ���� + ÷������ ����
		if(service.delete_i(bno)) {
			//�� ���� ���� ����
			deleteFiles_i(attachList);
			rttr.addFlashAttribute("result","����");
		}	
		
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:inq_board_list";
	}
	
	
	
	//÷�ι� �������� -f
	@GetMapping("/getAttachList")
	public ResponseEntity<List<FreeAttachFileDTO>> getAttachList_f(int bno){
		log.info("÷�ι� �������� "+bno);
		
		return new ResponseEntity<List<FreeAttachFileDTO>>(service.getAttachList_f(bno),HttpStatus.OK);
	}
	
	//÷�ι� �������� -p
	@GetMapping("/getAttachList")
	public ResponseEntity<List<ProAttachFileDTO>> getAttachList_p(int bno){
		log.info("÷�ι� �������� "+bno);
		
		return new ResponseEntity<List<ProAttachFileDTO>>(service.getAttachList_p(bno),HttpStatus.OK);
	}
	
	//÷�ι� �������� -q
	@GetMapping("/getAttachList")
	public ResponseEntity<List<InqAttachFileDTO>> getAttachList_i(int bno){
		log.info("÷�ι� �������� "+bno);
		
		return new ResponseEntity<List<InqAttachFileDTO>>(service.getAttachList_i(bno),HttpStatus.OK);
	}
	
	
	//���� ���� -f
	private void deleteFiles_f(List<FreeAttachFileDTO> attachList) {
		log.info("÷������ ���� "+attachList);
		
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
	
	//���� ���� -p
	private void deleteFiles_p(List<ProAttachFileDTO> attachList) {
		log.info("÷������ ���� "+attachList);
		
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
	
	//���� ���� -i
	private void deleteFiles_i(List<InqAttachFileDTO> attachList) {
		log.info("÷������ ���� "+attachList);
		
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






















