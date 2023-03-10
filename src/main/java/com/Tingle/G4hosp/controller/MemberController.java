package com.Tingle.G4hosp.controller;

import java.util.List;

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
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.service.MedService;
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
	private final MedService medService;

	// 로그인 화면
	@GetMapping(value = "/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}

	// 로그인 실패시.
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "member/memberLoginForm";
	}

	// 회원가입 화면
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		MemberFormDto dto = new MemberFormDto();
		dto.setMed(medService.getMedList());
		System.err.println(dto);
		model.addAttribute("memberFormDto", dto);
		
//		List<Med> meds = medService.getMedList();
//		System.out.println(meds);
//		model.addAttribute("meds", meds);
		
		return "member/memberForm";
		
	}

	// 회원가입 버튼 클릭
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
			@RequestParam("profileImg") MultipartFile file) {
		System.err.println(memberFormDto);
		if (bindingResult.hasErrors()) {
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

	// ID찾기화면
	@GetMapping(value = "/find")
	public String memberFindId(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberFindId";

	}

	// ID찾기
	@PostMapping(value = "/find")
	public String memberFindId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		try {
			Member memberFindID = memberService.findByMnameMtel(memberFormDto.getName(), memberFormDto.getTel());
			System.out.println("llllllllllllllll"+ memberFindID.getLoginid());
			model.addAttribute("findID", memberFindID);
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
			return "member/memberFindId";
		}

//		return "member/memberFindIdResult";		
	}

	// id찾기 결과화면
	@GetMapping(value = "/findResult")
	public String memberFindResult(MemberFormDto memberFormDto, Model model) {

		return "member/memberFindIdResult";
	}

	@PostMapping(value = "/findResult")
	public String memberFindResult(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		Member memberFindID = memberService.findByMnameMtel(memberFormDto.getName(), memberFormDto.getTel());
		System.out.println("llllllllllllllll"+ memberFindID.getPwd());
		model.addAttribute("findID", memberFindID);
		System.out.println(memberFindID.getLoginid());
		return "member/memberFindIdResult";
	}

}
