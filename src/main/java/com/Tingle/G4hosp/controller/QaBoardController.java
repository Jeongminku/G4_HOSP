package com.Tingle.G4hosp.controller;

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
	
	@GetMapping(value="/new")
	public String qnaHome(QaBoardDto qaBoardDto) {
		
		return "qaPage/qaForm";
	}
	
//	@PostMapping(value="/new")
//	public String newQaBoard(QaBoardDto qaBoardDto, Model model) {
//		
//		String loginid = SecurityContextHolder.getContext().getAuthentication().getName();
//		Member member = memberService.findByLoginid(loginid);
//		QaBoard qaBoard = QaBoard.createQaBoard(qaBoardDto, member);
//		qaBoardService.saveQaBoard(qaBoard);
//
//		return "redirect:/qa/";
//	}
	@PostMapping(value="/new")
	public String newQaBoard(QaBoardDto qaBoardDto, Model model) {
		
		QaBoard qaBoard = QaBoard.createQaTest(qaBoardDto);
		qaBoardService.saveQaBoard(qaBoard);

		return "redirect:/qa/";
	}
}
