package com.Tingle.G4hosp.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.dto.DiseaseFormDto;
import com.Tingle.G4hosp.dto.HinfoBoardDto;
import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.dto.ReservationViewDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.HospitalizeDisease;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.entity.QuickReservation;
import com.Tingle.G4hosp.repository.HospitalizeDiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeRepository;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.service.AdminService;
import com.Tingle.G4hosp.service.ChatRoomAccessService;
import com.Tingle.G4hosp.service.ChatService;
import com.Tingle.G4hosp.service.DiseaseService;
import com.Tingle.G4hosp.service.HospitalizeService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.QuickReservationService;
import com.Tingle.G4hosp.service.ReservationService;

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
	private final AdminService adminService;
	private final ChatService chatService;	
	private final ChatRoomAccessService chatRoomAccessService;
	private final ReservationService reservationService;
	private final HospitalizeRepository hospitalizeRepository;
	private final HospitalizeDiseaseRepository hospitalizeDiseaseRepository;
	private final MemberMedRepository memberMedRepository;
	private final MedRepository medRepository;
	private final MemberRepository memberRepository;
	
	// 관리자 페이지 화면
	@GetMapping
	public String adminPage() {
		return "adminPage/adminPage";
	}
	
	@RequestMapping( value ="/chart" , method = {RequestMethod.POST})
	@ResponseBody AdminMainDto adminChart() {
		
		return adminService.getChartList();
	}
	
//	고객 게시판
	@GetMapping({"/board/{Path}", "/board/{Path}/{Id}"}) 
	public String boardGet (@PathVariable(name = "Path", required = false)String path, 
							@PathVariable(name = "Id", required = false)Long id, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		try {
			Long boardId = Long.parseLong(path);
			return "forward:/board/" + boardId;
		} catch (Exception e) {
			if(StringUtils.equals(path, "main")) return "forward:/board/main";
			if(StringUtils.equals(path, "write")) return "forward:/board/write";
			if(StringUtils.equals(path, "boardsave")) return "forward:/board/boardsave";
			if(StringUtils.equals(path, "upDateForm")) return "forward:/board/upDateForm/" + id;
			if(StringUtils.equals(path, "delBoard")) return "forward:/board/delBoard/" + id;
		}
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/board/main", resp);
	}
	
	@PostMapping("/board/{Path}") 
	public String boardPost (@PathVariable("Path")String path, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		if(StringUtils.equals(path, "boardsave")) return "forward:/board/boardsave";
		if(StringUtils.equals(path, "updateB")) return "forward:/board/updateB/";
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/board/main", resp);
	}
	
//	건강정보 게시판
	@GetMapping({"/Hinfo/{Path}", "/Hinfo/{Path}/{Id}"})
	public String hinfoGet (@PathVariable(name = "Path", required = false)String path, 
							@PathVariable(name = "Id", required = false)Long id, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		try {
			Long hinfoId = Long.parseLong(path);
			return "forward:/Hinfo/" + hinfoId;
		} catch (Exception e) {
			if(StringUtils.equals(path, "HinfoMain")) return "forward:/Hinfo/HinfoMain";			
			if(StringUtils.equals(path, "write")) return "forward:/Hinfo/write";		
			if(StringUtils.equals(path, "updatepage")) return "forward:/Hinfo/updatepage/" + id;		
			if(StringUtils.equals(path, "deletepage")) return "forward:/Hinfo/deletepage/" + id;		
		}
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/Hinfo/HinfoMain", resp);
	}

	@PostMapping({"/Hinfo/{Path}", "/Hinfo/{Path}/{Id}"}) 
	public String hinfoPost (@PathVariable("Path")String path, @PathVariable(name = "Id", required = false)Long id, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		if(StringUtils.equals(path, "Hinfo")) return "forward:/Hinfo/Hinfo";
		if(StringUtils.equals(path, "updatepage")) return "forward:/Hinfo/updatepage/" + id;
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/Hinfo/HinfoMain", resp);
	}

//	qa게시판
	@GetMapping({"/qa", "/qa/{Path}", "/qa/{Path}/{Id}"}) 
	public String qaGet (@PathVariable(name = "Path", required = false)String path, 
							@PathVariable(name = "Id", required = false)Long id, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		try {
			Long boardId = Long.parseLong(path);
			return "forward:/board/" + boardId;
		} catch (Exception e) {
			if(path == null) return "forward:/qa";
			if(StringUtils.equals(path, "new")) return "forward:/qa/new";
			if(StringUtils.equals(path, "pay")) return "forward:/qa/pay";
			if(StringUtils.equals(path, "hspdsc")) return "forward:/qa/hspdsc";
			if(StringUtils.equals(path, "mod")) return "forward:/qa/mod/" + id;
			if(StringUtils.equals(path, "del")) return "forward:/qa/del/" + id;
		}
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/qa", resp);
	}
	
	@PostMapping({"/qa/{Path}", "/qa/{Path}/{Id}"}) 
	public String qaPost (@PathVariable("Path")String path, @PathVariable(name = "Id", required = false)Long id, HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isAdmin", true);
		if(StringUtils.equals(path, "new")) return "forward:/qa/new";
		if(StringUtils.equals(path, "mod")) return "forward:/qa/mod";
		return MemberCheckMethod.redirectAfterAlert("존재하지 않는 페이지입니다.", "/admin/qa", resp);
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
	
	
	
	// 진료과 삭제
	@RequestMapping("delmed/{medId}")
	public String delMed(@PathVariable("medId") Long medId, Model model, HttpServletResponse resp) {
		
		
		try {
			medService.delmed(medId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "진료과 삭제중 에러가 발생했습니다.");
			return "redirect:/admin/med";
		}

		
		model.addAttribute("medFormDto", new MedFormDto());
		
		List<Med> meds = medService.getMedList();
		model.addAttribute("meds", meds);
		
		return  MemberCheckMethod.redirectAfterAlert("진료과 삭제가 완료되었습니다.",  "/admin/med" , resp);
	}
	
	// 진료과 수정 페이지 이동
	@GetMapping("editmed/{medId}")
	public String editMed(@PathVariable("medId") Long medId, Model model,Authentication authentication, HttpServletResponse resp, MedFormDto medFormDto) {
		System.err.println(medId);
		Med med = medService.findbyid(medId);
		medFormDto.setMedId(med.getMedId());
		medFormDto.setMedInfo(med.getMedInfo());
		medFormDto.setMedName(med.getMedName());
		System.err.println(medFormDto);
		model.addAttribute("medFormDto",medFormDto);

		return "adminPage/medForm";
	}
	
	//진료과 수정 버튼 클릭
	@PostMapping("editmed")
	public String editMedPage(MedFormDto medFormDto, Model model, HttpServletResponse resp) {
		try {
			medService.saveMed(medFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "진료과 입력 중 에러가 발생하였습니다.");
			return "adminPage/medForm";
		}
		
		return MemberCheckMethod.redirectAfterAlert("진료과 내용이 변경되었습니다.",  "/admin/med" , resp);
	}
	
	// 병명 삭제
	@RequestMapping("deldisease/{diseaseId}")
	public String deldisease(@PathVariable("diseaseId") Long diseaseId, Model model, HttpServletResponse resp) {
		
		try {
			diseaseService.deldisease(diseaseId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "병명 삭제중 에러가 발생했습니다.");
			return "redirect:/admin/disease";
		}

		model.addAttribute("diseaseFormDto", new DiseaseFormDto());
		
		List<Disease> diseases = diseaseService.getDiseaseList();
		model.addAttribute("diseases", diseases);
		
		
		return MemberCheckMethod.redirectAfterAlert("병명삭제가 완료되었습니다.",  "/admin/disease" , resp);
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
		model.addAttribute("memberList", memberService.findMemberList("ALL"));
		return "adminPage/memberList";
	}
	
	@PostMapping("/memberList/{opt}")
	public String memberListFind (@PathVariable("opt")String opt, Model model, HttpServletResponse resp) {
		try {
			model.addAttribute("memberList", memberService.findMemberList(opt));
		} catch (Exception e) {
			MemberCheckMethod.redirectAfterAlert("목록을 불러오는중 오류가 발생했습니다.", "/admin", resp);
		}
		if(StringUtils.equals(opt, "DOCTOR")) return "adminPage/memberList :: memberList_DOCTOR";
		if(StringUtils.equals(opt, "HOSPITALIZE")) return "adminPage/memberList :: memberList_HOSP";
		if(StringUtils.equals(opt, "ALL")) return "adminPage/memberList :: memberList_ALL";
		return "adminPage/memberList :: memberList_CLIENT_ADMIN";
	}
	
//	// 입원환자목록 페이지 화면
//	@GetMapping(value="/hospitalizeList")
//	public String hospitalizeList(Model model) {
//		
////		List<Hospitalize> members = hospitalizeService.FindHosListByHosStatus();
////		model.addAttribute("members", members);
//		
//		List<HospitalizeDisease> memHosDis = hospitalizeDiseaseRepository.findAll();
//		model.addAttribute("memHosDis", memHosDis);
//		
//		System.err.println(memHosDis);
//		
//		return "adminPage/hospitalizeList";
//	}
//	
//	// 의사목록 페이지 화면
//	@GetMapping(value="/doctorList")
//	public String doctorList(Model model) {
//		
////		Role doctor = Role.DOCTOR;
//		
////		List<Member> members = memberService.getMemberList(doctor);
////		model.addAttribute("members", members);
//		
//		List<MemberMed> memberMeds = memberMedRepository.findAll();
//		
//		model.addAttribute("memberMeds", memberMeds);
//		
//		return "adminPage/doctorList";
//	}
	
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
	
//	채팅관련
	@GetMapping("/chat")
	public String chat (HttpServletRequest req) {
		req.setAttribute("isAdmin", true);
		return "forward:/chat";	    			
	}
	
	@PostMapping("/room")
    public String enterChatRoom () {
		return "forward:/chat/room";
    }
	
	@GetMapping("/chatControll")
	public String chatView (Model model) {
		model.addAttribute("AllAccessList", chatService.findAllAccessListToMap());
		model.addAttribute("AllChatRoom", chatService.findAllChatRoom());
		return "adminPage/adminChat";
	}
	
	@PostMapping("/chatControll")
    public String createRoom(ChatRoomDto chatRoomDto, Model model) {
    	try {
    		chatService.createChatRoom(chatRoomDto);
    	} catch(Exception e) {
    		model.addAttribute("ErrorMsg", e.getMessage());
    	}
        return "redirect:/admin/chatControll";
    }
	
	@PostMapping("/chatDel")
	public String deleteRoom (Long chatRoomId, Model model) {
		try {
			chatService.deleteChatRoom(chatRoomId);
		} catch (Exception e) {
			model.addAttribute("ErrorMsg", e.getMessage());			
		}
		return "redirect:/admin/chatControll";
	}
	
	@PostMapping("/chatEdit")
	public String editRoom (Long editchatRoomId, Long editChatAccess, String eidtChatName, Model model) {
		ChatRoomDto chatRoomDto = ChatRoomDto.createChatRoomDto(editchatRoomId, editChatAccess, eidtChatName);
		try {
			chatService.updateChatRoom(chatRoomDto);
		} catch (Exception e) {
			model.addAttribute("ErrorMsg", e.getMessage());		
		}
		return "redirect:/admin/chatControll";
	}
	
//	예약관리
	@GetMapping("/reservation")
	public String reservationListView (Model model, Principal principal) {
		try {
			List<ReservationViewDto> viewList = reservationService.findAllReservation();
			model.addAttribute("ViewList", viewList);
			model.addAttribute("isAdmin", true);
		} catch (Exception e) {
			model.addAttribute("Error", e.getMessage());
		}
		return "reservationPage/viewReservation";
	}
	
	//=========================================== 기능 테스트
	
	//////test
	@GetMapping("/test")
	public String test(){
		AdminMainDto adminMainDtot = new AdminMainDto();
		adminMainDtot = adminService.adminpagetest(adminMainDtot);
		
		//  의사 수, 환자수, 입원 환자 수, 과별 입원환자 카운트 리스트 		
		 System.err.println("의사수 확인 테스트 : "+adminMainDtot.getDoctorcount());
		 System.err.println("입원 환자 수 확인 테스트 : "+adminMainDtot.getHospitalizecount());
		 System.err.println("병상 가동률 확인 테스트 (병상 50개 기준): "+((adminMainDtot.getHospitalizecount()*100)/50)+"%");
		 System.err.println("환자수 확인 테스트 : "+adminMainDtot.getPatientcount());

		 // 중간길 : 각 호실별 입원 인원 현황 	
				
		for(int i=0; i<adminMainDtot.getHosptalizedEachMedname().size(); i++) {
			System.err.println("과 이름 목록 테스트 : "+adminMainDtot.getHosptalizedEachMedname().get(i));
			System.err.println(adminMainDtot.getHosptalizedEachMedname().get(i)+"의 입원 환자 수 테스트 : "+adminMainDtot.getHosptalizedEachMed().get(i));
		}
		
		for(int i=0; i<adminMainDtot.getHosptalizedEachWard().size(); i++) {
			System.err.println("호실별 입원 환자 현황 테스트 : " + adminMainDtot.getHosptalizedEachWardname().get(i));
			System.err.println(adminMainDtot.getHosptalizedEachWardname().get(i)+"호실 현재 입원 환자 수 : "+adminMainDtot.getHosptalizedEachWard().get(i));
		}
		
		
		
//		String test = "내";
//	
//		List<AdminMainDto> adminMainDtolist = new ArrayList<>();
//		
//		// 이름으로 의사 검색
//		List<Member> list = memberRepository.getdoctorbysearch(test);
//
//		// 의사가 속해있는 과 검색 및 dto 추가
//		for(int i=0; i<list.size(); i++) {
//			AdminMainDto adminMainDto = new AdminMainDto();
//			Med med = medRepository.findMedbyDocid(list.get(i).getId());
//			adminMainDto.setSearchdoctor(list.get(i));
//			adminMainDto.setSearchdoctormedname(med.getMedName());
//			adminMainDtolist.add(adminMainDto);
//		}
//	
//		for(AdminMainDto ad : adminMainDtolist) {
//			System.err.println("과로 검색 전 의사 정보 리스트 "+ ad.getSearchdoctormedname());
//			System.err.println("과로 검색 전 의사 정보 리스트 "+ ad.getSearchdoctor().getName());		
//		}
//		
//		//~ 과에 속해있는 의사 띄워주기
//		// 주어진 키워드로 과를 찾아온다
//		List<MemberMed> medlist1 = memberRepository.getMedDoctorbysearch(test);
//		List<Member> medmemlist = new ArrayList<>();
//		List<Member> medmemlistc = new ArrayList<>();
//		for(MemberMed med : medlist1) {
//			Member memlist = memberRepository.getReferenceById(med.getMemberId().getId());
//			medmemlist.add(memlist);
//			medmemlistc.add(memlist);
//		}	
//		
//		// 이름 검색, 과 검색 중복제거
//		for(int i=0; i<list.size(); i++) {
//			for(int j =0; j<medmemlist.size(); j++) {
//				if(list.get(i).getName() == medmemlist.get(j).getName()) {
//					medmemlistc.remove(j);
//				}				
//			}
//		}
//		
//		for(int i = 0; i<medmemlistc.size(); i++) {
//			AdminMainDto adminMainDto = new AdminMainDto();
//			Med med = medRepository.findMedbyDocid(medmemlistc.get(i).getId());
//			adminMainDto.setSearchdoctor(medmemlistc.get(i));
//			adminMainDto.setSearchdoctormedname(med.getMedName());
//			adminMainDtolist.add(adminMainDto);
//		}
//		
//		for(AdminMainDto ad : adminMainDtolist) {
//			System.err.println("과 검색 후 의사 정보 리스트 "+ ad.getSearchdoctormedname());
//			System.err.println("과 검색 후 의사 정보 리스트 "+ ad.getSearchdoctor().getName());		
//		}
			
		
		String test = "내";
	
		List<AdminMainDto> adminMainDtolist = new ArrayList<>();
		
		// 이름으로 의사 검색
		List<Member> list = memberRepository.getdoctorbysearch(test);
		System.err.println("'내'가 이름에 들어가있는 의사 list : "+list);
		// 의사가 속해있는 과 검색 및 dto 추가
		for(int i=0; i<list.size(); i++) {
			AdminMainDto adminMainDto = new AdminMainDto();
			Med med = medRepository.findMedbyDocid(list.get(i).getId());
			adminMainDto.setSearchDocId(list.get(i).getId());
			adminMainDto.setSearchDocImgOri(list.get(i).getImgOri());
			adminMainDto.setSearchDocImgUrl(list.get(i).getImgUrl());
			adminMainDto.setSearchDocName(list.get(i).getName());
			adminMainDto.setSearchDocMedName(med.getMedName());
			adminMainDtolist.add(adminMainDto);
		}
	
		
		for(AdminMainDto m : adminMainDtolist) {
			System.err.println("@@@@@@@ 진료과, 의사 명 테스트 @@@@@@@@");
			System.err.println("의사명 : "+m.getSearchDocName());
			System.err.println("진료과명 : "+m.getSearchDocMedName());
		}
		
		return "adminPage/adminPage";
	}
	
}