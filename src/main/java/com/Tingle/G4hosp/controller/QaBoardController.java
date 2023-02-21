package com.Tingle.G4hosp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.constant.QaCategory;
import com.Tingle.G4hosp.dto.QaBoardDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.QaBoard;
import com.Tingle.G4hosp.repository.QaBoardRepository;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.QaBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/qa")
@RequiredArgsConstructor
public class QaBoardController {
	
	private final MemberService memberService;
	private final QaBoardService qaBoardService;
	
	//QA 메인 & 카테고리별 클릭했을때 띄워줄 화면
	@GetMapping(value={"/","/{category}"})
	public String pageroute(Model model,@PathVariable("category") Optional<String> case1) {
		List<QaBoard> qaList = null;
		if(case1.isPresent()) {
				qaList = qaBoardService.findQaFromCategory(case1.get());
				System.out.println(qaList);
				model.addAttribute("qaList", qaList);
		} else {
			List<QaBoard> QaBoardAllList = qaBoardService.findAllQaBoard();
			model.addAttribute("qaList", QaBoardAllList);
		}
		return "qaPage/qaPage";
	}
	
	//QA 작성 페이지 보기
	@GetMapping(value="/new")
	public String qnaForm(QaBoardDto qaBoardDto) {
		return "qaPage/qaForm";
	}
	
	
	//로그인아이디 안넣고 글세이브하는 기능(테스트용) 
	@PostMapping(value="/new")
	public String newQaBoard(QaBoardDto qaBoardDto, Model model) {
		
		QaBoard qaBoard = QaBoard.createQaTest(qaBoardDto);
		qaBoardService.saveQaBoard(qaBoard);
		
		return "redirect:/qa/";
	}

	@GetMapping(value="/mod/{qaId}")
	public String modQaBoard(Model model, @PathVariable("qaId") Long qaId, QaBoardDto qaBoardDto) {
		QaBoard qaBoard = qaBoardService.findQaBoard(qaId);
		System.out.println(qaBoard);
		qaBoardDto.setId(qaBoard.getId());
		qaBoardDto.setTitle(qaBoard.getQaTitle());
		qaBoardDto.setContent(qaBoard.getQaContent());
		qaBoardDto.setCategory(qaBoard.getQaCategory());
		System.out.println(qaBoard);
		System.out.println("@@@@@@@@@@ "+qaBoard.getId());
		return "qaPage/qaModify";
	}
	
	@PostMapping(value="/mod")
	public String modQQaBoard(QaBoardDto qaBoardDto, Model model) {
		qaBoardService.modifyQaBoard(qaBoardDto);
		List<QaBoard> QaBoardAllList = qaBoardService.findAllQaBoard();
		model.addAttribute("qaList", QaBoardAllList);
		return "qaPage/qaPage";
	}
	
				
	
	//	@PostMapping(value="/new2")
//	public String newQaBoard2(QaBoardDto qaBoardDto, Model model) {
//		
//		String loginid = SecurityContextHolder.getContext().getAuthentication().getName();
//		Member member = memberService.findByLoginid(loginid);
//		QaBoard qaBoard = QaBoard.createQaBoard(qaBoardDto, member);
//		qaBoardService.saveQaBoard(qaBoard);
//
//		return "redirect:/qa/";
//	}
	
	
}
