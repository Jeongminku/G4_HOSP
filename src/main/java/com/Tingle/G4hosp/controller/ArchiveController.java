package com.Tingle.G4hosp.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/archive")
@Controller
@RequiredArgsConstructor
public class ArchiveController {

	//회원 진료차트 화면
	@GetMapping(value="/main")
	public String loginMember(Member member, Model model) {
		
		
		
		model.addAttribute("member", member);
		return "ArchivePage/ArchiveView";
	}
	
	
}
