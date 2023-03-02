package com.Tingle.G4hosp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.dto.DiseaseFormDto;
import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.HospitalizeDisease;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.entity.QuickReservation;
import com.Tingle.G4hosp.repository.HospitalizeDiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.service.ChatService;
import com.Tingle.G4hosp.service.DiseaseService;
import com.Tingle.G4hosp.service.HospitalizeService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberMedService;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.QuickReservationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final MemberService memberService;
	private final MedService medService;
	private final DiseaseService diseaseService;
	private final HospitalizeService hospitalizeService;
	private final QuickReservationService quickReservationService;
	private final HospitalizeRepository hospitalizeRepository;
	private final HospitalizeDiseaseRepository hospitalizeDiseaseRepository;
	private final MemberMedRepository memberMedRepository;
	private final ChatService chatService;
	
	
	// 관리자 페이지 화면
	@GetMapping(value="/adminpage")
	public String adminPage() {
		return "adminPage/adminPage";
		
	}
	
//	===================================================================================
	
	// 진료과 입력 화면
	@GetMapping(value="/med")
	public String medForm(Model model) {
		model.addAttribute("medFormDto", new MedFormDto());
		
		List<Med> meds = medService.getMedList();
		model.addAttribute("meds", meds);
		
		return "adminPage/medForm";
	}
	
	// 진료과 입력 기능
	@PostMapping(value = "/med")
	public String medFormInput(@Valid MedFormDto medFormDto, BindingResult bindingResult, Model model) {
		
		try {
			medService.saveMed(medFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "진료과 입력 중 에러가 발생하였습니다.");
			return "adminPage/medForm";
		}
		
		return "redirect:/admin/med";
	}
	
	// 병명 입력 화면
	@GetMapping(value="/disease")
	public String diseaseForm(Model model) {
		model.addAttribute("diseaseFormDto", new DiseaseFormDto());
		
		List<Disease> diseases = diseaseService.getDiseaseList();
		model.addAttribute("diseases", diseases);
		List<Med> meds = medService.getMedList();
		model.addAttribute("meds", meds);
		
		return "adminPage/diseaseForm";
	}
	
	// 병명 입력 기능
	@PostMapping(value="/disease")
	public String diseaseFormInput(@Valid DiseaseFormDto diseaseFormDto, BindingResult bindingResult, Model model) {
		System.out.println("VIEW -> CONT test : " + diseaseFormDto.getMed());
		try {
			diseaseService.saveDisease(diseaseFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "병명 입력 중 에러가 발생하였습니다.");
			return "adminPage/diseaseForm";
		}
		
		return "redirect:/admin/disease";
		
	}
	
//	===================================================================================
	
	// 고객목록 페이지 화면
	@GetMapping(value="/memberList")
	public String memberList(Model model) {
		
		Role client = Role.CLIENT;
		
		List<Member> members = memberService.getMemberList(client);
		model.addAttribute("members", members);
		
		return "adminPage/memberList";
	}
	
	// 입원환자목록 페이지 화면
	@GetMapping(value="/hospitalizeList")
	public String hospitalizeList(Model model) {
		
//		List<Hospitalize> members = hospitalizeService.FindHosListByHosStatus();
//		model.addAttribute("members", members);
		
		List<HospitalizeDisease> memHosDis = hospitalizeDiseaseRepository.findAll();
		model.addAttribute("memHosDis", memHosDis);
		
		System.err.println(memHosDis);
		
		return "adminPage/hospitalizeList";
	}
	
	// 의사목록 페이지 화면
	@GetMapping(value="/doctorList")
	public String doctorList(Model model) {
		
//		Role doctor = Role.DOCTOR;
		
//		List<Member> members = memberService.getMemberList(doctor);
//		model.addAttribute("members", members);
		
		List<MemberMed> memberMeds = memberMedRepository.findAll();
		
		model.addAttribute("memberMeds", memberMeds);
		
		return "adminPage/doctorList";
	}
	
	// 비회원 예약환자 조회
	@GetMapping("/qlist")
	public String qrlist(Model model) {
		List<QuickReservation> QRlist = quickReservationService.QRList();
		model.addAttribute("QRlist",QRlist);
		return "adminPage/QuickReservationList";
	}
	// 비회원 예약환자 처리 (N -> Y)
	@GetMapping("/updateqr/{id}")
	public String updateqr(Model model, @PathVariable("id")Long qrid) {
		quickReservationService.updateQR(qrid);
		List<QuickReservation> QRlist = quickReservationService.QRList();
		model.addAttribute("QRlist",QRlist);
		return "adminPage/QuickReservationList";
	}
	// 비회원 예약 환자 삭제
	@GetMapping("/deleteqr/{id}")
	public String deleteqr(Model model, @PathVariable("id")Long qrid) {
		quickReservationService.deleteQR(qrid);
		List<QuickReservation> QRlist = quickReservationService.QRList();
		model.addAttribute("QRlist",QRlist);
		return "adminPage/QuickReservationList";
	}
	
	@GetMapping("/chat")
	public String chatView (Model model) {
		model.addAttribute("AllAccessList", chatService.findAllAccessListToMap());
		model.addAttribute("AllChatRoom", chatService.findAllChatRoom());
		return "adminPage/AdminChat";
	}
	
	@PostMapping("/chat")
    public String createRoom(ChatRoomDto chatRoomDto, Model model) {
    	try {
    		chatService.createChatRoom(chatRoomDto);
    	} catch(Exception e) {
    		model.addAttribute("ErrorMsg", e.getMessage());
    	}
        return "redirect:/admin/chat";
    }
	
	@PostMapping("/chatDel")
	public String deleteRoom (Long chatRoomId, Model model) {
		try {
			chatService.deleteChatRoom(chatRoomId);
		} catch (Exception e) {
			model.addAttribute("ErrorMsg", e.getMessage());			
		}
		return "redirect:/admin/chat";
	}
	
}
