package com.Tingle.G4hosp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	// 관리자 페이지 화면
	@GetMapping(value="/adminpage")
	public String loginMember() {
		return "adminPage/adminPage";
		
	}
	
	
	
}
