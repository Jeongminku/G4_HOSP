package com.Tingle.G4hosp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.QaBoardDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.QaBoard;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.QaBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/qa")
@RequiredArgsConstructor
public class QaBoardController {
	
	private final MemberService memberService;
	private final QaBoardService qaBoardService;
	
	@GetMapping(value="/qaPage")
	public String qnaHome(Model model) {
		
		List<QaBoard> QaBoardAllList = qaBoardService.findAllQaBoard();
		model.addAttribute("QaAllList", QaBoardAllList);
		System.out.println(QaBoardAllList);
		
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
	
	@GetMapping(value="/new")
	public String qnaForm(QaBoardDto qaBoardDto) {
		return "qaPage/qaForm";
	}
	

	//로그인아이디 안넣고 글세이브하는 기능(테스트용)
	@PostMapping(value="/new")
	public String newQaBoard(QaBoardDto qaBoardDto, Model model) {
		
		QaBoard qaBoard = QaBoard.createQaTest(qaBoardDto);
		qaBoardService.saveQaBoard(qaBoard);

		return "redirect:/qa/qaPage";
	}
	

}
