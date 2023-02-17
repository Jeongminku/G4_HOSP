package com.Tingle.G4hosp.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.MemberFormDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final MemberImgService memberImgService;
	private final PasswordEncoder passwordEncoder;

	//로그인 화면
	@GetMapping(value="/login")
	public String loginMember() {
		return "member/memberLoginForm";
		
	}
	
	//로그인 실패시.
	@GetMapping(value="/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "member/memberLoginForm";
	}
	
	//회원가입 화면
	@GetMapping(value="/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입 버튼 클릭
	@PostMapping(value="/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, @RequestParam("profileImg") MultipartFile file) {
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
		memberService.saveMember(memberFormDto, file);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/";
	}
	
}
