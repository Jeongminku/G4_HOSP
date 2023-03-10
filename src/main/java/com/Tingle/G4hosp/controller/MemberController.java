package com.Tingle.G4hosp.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
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

import com.Tingle.G4hosp.constant.Role;
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
	
		
	
	//회원가입선택화면
		@GetMapping(value = "/new")
		public String memberFormSel() {
			return "member/memberSelect";
		}
		
		// 일반인회원가입 화면
		@GetMapping(value = "/new/client")
		public String memberclientForm(Model model) {
			MemberFormDto dto = new MemberFormDto();
			dto.setMed(medService.getMedList());
			model.addAttribute("memberFormDto", dto);
			return "member/memberClientForm";
		}

		// 일반인회원가입 버튼 클릭
		@PostMapping(value = "/new/client")
		public String memberclientForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
				@RequestParam("profileImg") MultipartFile file, HttpServletResponse resp) {
			//System.err.println(memberFormDto.getMedId());
			if (bindingResult.hasErrors()) {
				return "member/memberClientForm";
			} 
			
			try {
				memberService.saveMember(memberFormDto, file);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", e.getMessage());
				return "member/memberClientForm";
			}
			return MemberCheckMethod.redirectAfterAlert("회원가입이 완료되었습니다.", "/", resp);
		}
		
		// 의료진회원가입 화면
				@GetMapping(value = "/new/doctor")
				public String memberdocotrForm(Model model) {
					MemberFormDto dto = new MemberFormDto();
					dto.setMed(medService.getMedList());
					model.addAttribute("memberFormDto", dto);
					return "member/memberDoctorForm";
				}

				// 의료진회원가입 버튼 클릭
				@PostMapping(value = "/new/doctor")
				public String memberdocotrForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
						@RequestParam("profileImg") MultipartFile file, HttpServletResponse resp) {
					//System.err.println(memberFormDto.getMedId());
					if (bindingResult.hasErrors()) {
						return "member/memberDoctorForm";
					}
					try {
						memberService.saveMember(memberFormDto, file);
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMessage", e.getMessage());
						return "member/memberDoctorForm";
					}
					return MemberCheckMethod.redirectAfterAlert("회원가입이 완료되었습니다.", "/", resp);
				}
				
				//관리자 회원가입 화면
				@GetMapping(value = "/new/admin")
				public String memberadminForm(Model model) {
					MemberFormDto dto = new MemberFormDto();
					dto.setMed(medService.getMedList());
					model.addAttribute("memberFormDto", dto);
					return "member/memberAdminForm";
				}

				//관리자 회원가입 버튼 클릭
				@PostMapping(value = "/new/admin")
				public String memberadminForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
						@RequestParam("profileImg") MultipartFile file, HttpServletResponse resp) {
					//System.err.println(memberFormDto.getMedId());
					if (bindingResult.hasErrors()) {
						return "member/memberAdminForm";
					}
					try {
						memberService.saveMember(memberFormDto, file);
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMessage", e.getMessage());
						return "member/memberAdminForm";
					}
					return MemberCheckMethod.redirectAfterAlert("회원가입이 완료되었습니다.", "/", resp);
				}
		
	
	@GetMapping("/new/{loginid}")
	public @ResponseBody ResponseEntity<Integer> idCheck(@PathVariable("loginid") String loginid) {
		Integer result = memberService.vaildateDuplicateId(loginid);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/news/{tel}")
	public @ResponseBody ResponseEntity<Integer> telCheck(@PathVariable("tel") String tel) {
		Integer telresult = memberService.vaildateDuplicateTel(tel);
		
		return new ResponseEntity<Integer>(telresult, HttpStatus.OK);
	}
	
	
	// ID찾기화면
	@GetMapping(value = "/FindId")
	public String memberFindId(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberFindId";

	}

	// 아이디찾기 결과
	@PostMapping(value = "/FindIdResult")
	public String memberFindResult(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, HttpServletResponse resp) {
		Member memberFindID = memberService.findByMnameMtel(memberFormDto.getName(), memberFormDto.getTel());
		if(memberFindID == null) {
			return MemberCheckMethod.redirectAfterAlert("존재하지 않는 회원입니다.", "/members/FindId", resp);
		} else {
			model.addAttribute("findID", memberFindID);						
		}
		return "member/memberFindIdResult";
	}
	
	// 비밀번호 찾기(변경)화면
		@GetMapping(value="/FindPwd")
		public String memberFindPwd(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "member/memberFindPwd";
		}
	//비밀번호 찾기 결과
		@PostMapping(value="/FindPwdResult")
		public String memberFindPwdResult2(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,HttpServletResponse resp) {
			Member memberFindPwd = memberService.findByPwd(memberFormDto.getLoginid(), memberFormDto.getName(), memberFormDto.getTel());
			if(memberFindPwd == null) {
				return MemberCheckMethod.redirectAfterAlert("존재하지 않는 회원입니다.", "/members/FindPwd", resp);
			} else {
				String newPwd = memberService.updatenewPwd(memberFormDto, memberFormDto.getLoginid(), memberFormDto.getName(), memberFormDto.getTel());
				model.addAttribute("newPwd", newPwd);
			}
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
		return "reservationPage/viewReservation";
	}

	@GetMapping(value ="/myPage")
	public String memberMypage() {
		return "member/memberMyPage";
	}
	
	@GetMapping(value ="/archive")
	public String memberArchive(MemberFormDto memberFormDto, Principal principal, Model model) {
		String loginId = principal.getName();
		Member member = memberService.findByLoginid(loginId);
		memberFormDto = memberService.checkARdateandMedname(memberFormDto, member);
		System.err.println("컨트롤러 환자 진료 일자 리스트 출력 테스트 : "+ memberFormDto.getArchivedate());
		System.err.println("컨트롤러 환자 내원 과 리스트 출력 테스트 : "+ memberFormDto.getMedname());
		model.addAttribute("membername", member.getName());
		if(memberFormDto.getArchivedate() != null) {
			model.addAttribute("memberArchiveDate",memberFormDto.getArchivedate());
			model.addAttribute("memberArchiveMed", memberFormDto.getMedname());
			model.addAttribute("memberArchive", memberFormDto.getArchive());			
		}
		
		return "member/memberArchive";
	}
	
	@GetMapping(value = "/modify")
	public String memberModify(Model model, Principal principal) {
		String loginId = principal.getName();
		Member member = memberService.findByLoginid(loginId);
		MemberFormDto memberFormDto = new MemberFormDto();
		if(member.getRole().equals(Role.DOCTOR)) {
			Med med = medService.findMedbyDocid(member.getId()); //medId, medName, medInfo 가져옴.
			if(med != null) {
				List<Med> medlist = medService.getTesListNotMy(med.getMedId());
//			List<Med> medlist = medRepository.getMedListNotMyMed(med.getMedId());
				memberFormDto.setMed(medlist);			
			}
		}
		
		// 환자별 내원일자, 내원 과 조회
		memberFormDto = memberService.checkARdateandMedname(memberFormDto, member);		
		System.err.println("컨트롤러 환자 진료 일자 리스트 출력 테스트 : "+ memberFormDto.getArchivedate());
		System.err.println("컨트롤러 환자 내원 과 리스트 출력 테스트 : "+ memberFormDto.getMedname());

		model.addAttribute("memberFormDto",memberFormDto);
		model.addAttribute("modiMember", member);
		if(member.getRole().equals(Role.DOCTOR)) {
			MemberMed memberMed = memberMedService.findMemberMed(member.getId());
			model.addAttribute("memberMed", memberMed);
		}
		
		return "member/memberModify";
	}
	
	@PostMapping(value="/modify")
	public String memberModify(MemberFormDto memberFormDto, Model model, Principal principal, @RequestParam(name = "profileImg", required = false) MultipartFile file, HttpServletResponse resp) {
		String loginId = principal.getName();
		System.err.println(file);
		if(file != null) {
			if(!file.isEmpty()) {
				memberService.updateMember(memberFormDto, loginId, file);			
			} else {
				memberService.updateMember(memberFormDto, loginId);
			}			
		} else {
			memberService.updateMember(memberFormDto, loginId);
		}
				
		return MemberCheckMethod.redirectAfterAlert("정보수정이 완료되었습니다.", "/", resp);
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


