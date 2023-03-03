package com.Tingle.G4hosp.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.dto.MemberFormDto;
import com.Tingle.G4hosp.dto.MessageDto;
import com.Tingle.G4hosp.dto.ReservationViewDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MedRepository2;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberMedService;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final MemberImgService memberImgService;
	private final PasswordEncoder passwordEncoder;
	private final MedService medService;
	private final ReservationService reservationService;
	private final MemberMedService memberMedService;
	private final MedRepository medRepository;

	
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
		model.addAttribute("memberFormDto", dto);
		return "member/memberForm";
	}

	// 회원가입 버튼 클릭
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
			@RequestParam("profileImg") MultipartFile file) {
		//System.err.println(memberFormDto.getMedId());
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
	
	@GetMapping("/new/{loginid}")
	public @ResponseBody ResponseEntity<Integer> idCheck(@PathVariable("loginid") String loginid) {
		Integer result = memberService.vaildateDuplicateId(loginid);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	// ID찾기화면
	@GetMapping(value = "/FindId")
	public String memberFindId(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberFindId";

	}

	// ID찾기
	@PostMapping(value = "/FindId")
	public String memberFindId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		try {
			Member memberFindID = memberService.findByMnameMtel(memberFormDto.getName(), memberFormDto.getTel());
			System.out.println("llllllllllllllll"+ memberFindID.getLoginid());
			model.addAttribute("findID", memberFindID);
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
			return "member/memberFindIdResult";
		}

//		return "member/memberFindIdResult";		
	}

	// id찾기 결과화면
	@GetMapping(value = "/FindIdResult")
	public String memberFindResult(MemberFormDto memberFormDto, Model model) {

		return "member/memberFindIdResult";
	}
	
	@PostMapping(value = "/FindIdResult")
	public String memberFindResult(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		Member memberFindID = memberService.findByMnameMtel(memberFormDto.getName(), memberFormDto.getTel());
		System.out.println("llllllllllllllll"+ memberFindID.getPwd());
		model.addAttribute("findID", memberFindID);
		System.out.println(memberFindID.getLoginid());
		return "member/memberFindIdResult";
	}
	
	// 비밀번호 찾기(변경)화면
		@GetMapping(value="/FindPwd")
		public String memberFindPwd(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "member/memberFindPwd";
		}
		// 비밀번호 찾기(변경)
		@PostMapping(value="/FindPwd")
		public String memberFindPwd(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
			try {
				Member memberFindPwd = memberService.findByPwd(memberFormDto.getLoginid(), memberFormDto.getName(), memberFormDto.getTel());
				System.out.println("pppppp" + memberFindPwd.getPwd());
				model.addAttribute("findPwd", memberFindPwd);
			} catch (Exception e) {
				return "member/memberFindPwd";
			}
			return "member/memberFindPwdResult";
		}
		
		// 비번찾기 결과
		@GetMapping(value="/FindPwdResult")
		public String memberFindPwdResult(MemberFormDto memberFormDto, Model model) {
			return "member/memberFindPwdResult";
		}
	
		@PostMapping(value="/FindPwdResult")
		public String memberFindPwdResult2(MemberFormDto memberFormDto,Model model) {
			String newPwd = memberService.updatenewPwd(memberFormDto, memberFormDto.getLoginid(), memberFormDto.getName(), memberFormDto.getTel());
			model.addAttribute("newPwd",newPwd);
			return "member/memberFindPwdResult";
		}
	
		
	
	@GetMapping("/myReservation")
	public String reservationListView (Model model, Principal principal) {
		try {
			List<ReservationViewDto> viewList = reservationService.findAllReservationByMember(principal.getName());
			model.addAttribute("NotAvail", new ArrayList<>());
			model.addAttribute("ViewList", viewList);
		} catch (Exception e) {
			model.addAttribute("Error", e.getMessage());
		}
		return "ReservationPage/ViewReservation";
	}

	@GetMapping(value = "/modify")
	public String memberModify(Model model, Principal principal) {
		String loginId = principal.getName();
		Member member = memberService.findByLoginid(loginId);
		Med med = medService.findMedbyDocid(member.getId()); //medId, medName, medInfo 가져옴.
		
		
		
		MemberFormDto memberFormDto = new MemberFormDto();
		if(med != null) {
			List<Med> medlist = medService.getTesListNotMy(med.getMedId());
//			List<Med> medlist = medRepository.getMedListNotMyMed(med.getMedId());
			memberFormDto.setMed(medlist);			
		}

		model.addAttribute("memberFormDto",memberFormDto);
		model.addAttribute("modiMember", member);
		
		
		MemberMed memberMed = memberMedService.findMemberMed(member.getId());
		model.addAttribute("memberMed", memberMed);
		
		return "member/memberModify";
	}
	
	@PostMapping(value="/modify")
	public String memberModify(MemberFormDto memberFormDto, Model model, Principal principal) {
		String loginId = principal.getName();
		memberService.updateMember(memberFormDto, loginId);
		return "member/memberLoginForm";
	}
	
	@GetMapping(value= "/del/{id}")
	public String deleteMember(Model model, @PathVariable("id") Long memberId) {
		MessageDto message;				
		try {
			String delMemberMsg = memberService.deleteMember(memberId);
			message = new MessageDto(delMemberMsg, "/");
			SecurityContextHolder.clearContext();
		} catch (Exception e) {
			message = new MessageDto("회원 탈퇴에 실패했습니다.", "/");
		}
		
		return showMessageAndRedirect(message, model);
	}
	
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}
}	


