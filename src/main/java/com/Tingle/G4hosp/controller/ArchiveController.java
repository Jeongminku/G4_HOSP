package com.Tingle.G4hosp.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.ArchiveFormDto;
import com.Tingle.G4hosp.dto.ArchiveSearchDto;
import com.Tingle.G4hosp.dto.ArchiveViewDto;
import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.ArchiveDisease;
import com.Tingle.G4hosp.entity.ArchiveImg;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.ArchiveDiseaseRepository;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.service.ArchiveImgService;
import com.Tingle.G4hosp.service.ArchiveService;
import com.Tingle.G4hosp.service.DiseaseService;
import com.Tingle.G4hosp.service.HospitalizeService;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/archive")
@Controller
@RequiredArgsConstructor
public class ArchiveController {
	private final MemberService memberService;
	private final ArchiveService archiveService;
	private final ArchiveImgService archiveImgService;
	private final HospitalizeService hospitalizeService;
	private final DiseaseService diseaseService;
	
	private final ArchiveRepository archiveRepository;
	private final MemberRepository memberRepository;
	private final DiseaseRepository diseaseRepository;
	private final ArchiveDiseaseRepository archiveDiseaseRepository;
	// SEARCH PATIENT PAGE
	@GetMapping(value="/")
	public String archivesearchpage(Model model) {
		ArchiveSearchDto archiveSearchDto = new ArchiveSearchDto();
		model.addAttribute("archiveSearchDto",archiveSearchDto);
		return "/ArchivePage/ArchiveSearch";
	}
	
	// PATIENT SEARCH RESULT
	@PostMapping(value = "/patientsearch")
	public String patientresult(Model model, @Valid ArchiveSearchDto archiveSearchDto) {
		List<Member> patient = memberService.findMListbyMname(archiveSearchDto.getName());
		
		if(patient.isEmpty()) {
			model.addAttribute("errorMessage", "환자 이름이 조회되지 않습니다.");
			ArchiveSearchDto archiveSearchDto1 = new ArchiveSearchDto();
			model.addAttribute("archiveSearchDto",archiveSearchDto1);
			return "/ArchivePage/ArchiveSearch";
		}
		
		ArchiveSearchDto archiveSearchDto1 = new ArchiveSearchDto();
		model.addAttribute("patientlist",patient);
		model.addAttribute("archiveSearchDto",archiveSearchDto1);
		
		return "ArchivePage/ArchiveSearch";
	}
	
	// OPEN ARCHIVE PAGE
	@GetMapping(value = "/search/{id}")
	public String searcharchive1(Model model, @PathVariable("id") Optional<Long> patientid) {
		Member patientinfo = memberService.findByMemberid(patientid.get());
		Member patient = memberService.findByMemberid(patientid.get());
		List<Archive> AL = archiveService.returnArchive(patientinfo.getId());
		List<List<ArchiveImg>> AIL = new ArrayList<>();
		for(Archive A : AL) {
			AIL.add(archiveImgService.getarchiveimglist(A));
			List<ArchiveDisease> ADR = archiveDiseaseRepository.findAll();
		}
		List<ArchiveViewDto> AVDlist = new ArrayList<>();
		
		for(Archive archive : AL) {
			ArchiveViewDto archiveViewDto = new ArchiveViewDto();
			archiveViewDto.setARid(archive.getId());
			archiveViewDto.setARcreatedtime(archive.getArchiveCreatedtime());
			archiveViewDto.setARdoctorname(archive.getDoctorname());
			archiveViewDto.setARmodifiedtime(archive.getArchiveModifiedtime());
			archiveViewDto.setARdetail(archive.getDetail());
			archiveViewDto.setARdiseasename(diseaseService.findDiseasebyArcid(archive.getId()).getDiseaseName());
			AVDlist.add(archiveViewDto);
		}
		model.addAttribute("AVDlist",AVDlist);
		
		// CHECK PATIENT IS HOSPITALIZED OR NOT
		Hospitalize hospitalize;
		String hospYN;
		if(hospitalizeService.FindHosbymemid(patientid.get()) == null) {
			hospYN = "N";
		}else {
			hospitalize = hospitalizeService.FindHosbymemid(patientid.get());
			hospYN = hospitalize.getHospYN().toString();
		}
		model.addAttribute("hospYN",hospYN);
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patient.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);		
		model.addAttribute("age",age);
		
		
		model.addAttribute("ArchiveImgList",AIL);
		model.addAttribute("ArchiveList",AL);
		model.addAttribute("patientinfo",patientinfo);
		return "ArchivePage/ArchiveView";
	}

	@PostMapping(value = "/search")
	public String searcharchive(Model model, @Valid ArchiveSearchDto archiveSearchDto
			) {
		Member patientinfo = memberService.findByLoginid(archiveSearchDto.getLoginid());
		List<Archive> AL = archiveService.returnArchive(patientinfo.getId());
		List<List<ArchiveImg>> AIL = new ArrayList<>();
		for(Archive A : AL) {
			AIL.add(archiveImgService.getarchiveimglist(A));
		}
		
		// CHECK PATIENT IS HOSPITALIZED OR NOT
		Hospitalize hospitalize;
		String hospYN;
		if(hospitalizeService.FindHosbymemid(patientinfo.getId()) == null) {
			hospYN = "N";
		}else {
			hospitalize = hospitalizeService.FindHosbymemid(patientinfo.getId());
			hospYN = hospitalize.getHospYN().toString();
		}
		model.addAttribute("hospYN",hospYN);
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patientinfo.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);		
		model.addAttribute("age",age);
		
		model.addAttribute("ArchiveImgList",AIL);
		model.addAttribute("ArchiveList",AL);
		model.addAttribute("patientinfo",patientinfo);
		return "ArchivePage/ArchiveView";
	}
	
	// ARCHIVE WRITE PAGE
	@GetMapping(value={"/write","/write/{id}"})
	public String archivewrite(Model model,@PathVariable("id") Optional<Long> patientid, Principal principal) {
		String loginId = principal.getName();
		Member Doctor = memberService.findDocbyMid(loginId);
		ArchiveFormDto archiveFormDto = new ArchiveFormDto();
		Member patient = new Member();
		if(patientid.isPresent()) {
			patient = memberRepository.getReferenceById(patientid.get());	
		}
		archiveFormDto.setDoctorname(Doctor.getName());

		List<Disease> Diseaselist = diseaseService.findDiseaseListByDocId(Doctor.getLoginid());
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patient.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);		
		model.addAttribute("age",age);
		
		model.addAttribute("doctor", Doctor);
		model.addAttribute("archiveFormDto",archiveFormDto);
		model.addAttribute("patient", patient);
		model.addAttribute("DL",Diseaselist);
		return "/ArchivePage/ArchiveWrite";
	}
	
	// CLICK ARCHIVE WRITE BUTTON (CREATE)
	@PostMapping(value = {"/write","/write/{id}"})
	public String writearchive(Model model,@PathVariable("id") Optional<Long> patientid,
			@RequestParam("PostImgFile") List<MultipartFile> archiveImgFileList,
			@Valid ArchiveFormDto archiveFormDto, BindingResult bindingResult,
			@RequestParam("doctorname") String docname) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "값을 가져오는 중 에러가 발생했습니다!");
			return "ArchivePage/ArchiveWrite/"+patientid.get();
		}
		// CREATE & SAVE ARCHIVE
		try {
			archiveService.saveArchive(archiveFormDto, archiveImgFileList, patientid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "진료기록 작성 중 에러가 발생했습니다!");
			return "ArchivePage/ArchiveWrite/"+patientid.get();
		}
		Member patientinfo = memberService.findByMemberid(patientid.get());
		List<Archive> AL = archiveService.returnArchive(patientinfo.getId());	
		model.addAttribute("patientinfo",patientinfo);
		model.addAttribute("ArchiveList",AL);
		return "redirect:/archive/search/"+patientid.get();
	}
	
	// ARCHIVE UPDATE PAGE
	@GetMapping(value={"/update","/update/{id}"})
	public String archiveupdate(Model model,@PathVariable("id") Optional<Long> arcid, Principal principal) {
		String loginId = principal.getName();
		Member Doctor = memberService.findDocbyMid(loginId);
		List<Disease> Diseaselist = diseaseService.findDiseaseListByDocId(Doctor.getLoginid());
		
		ArchiveFormDto archiveFormDto = new ArchiveFormDto();		
		Archive archive = new Archive();
		if(arcid.isPresent()) {
			archive = archiveRepository.getReferenceById(arcid.get());
		}
		Member patient = memberRepository.getReferenceById(archive.getMember().getId());
		archiveFormDto.setId(patient.getId());
		archiveFormDto.setDoctorname(Doctor.getName());
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patient.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);	
		
		model.addAttribute("age",age);
		model.addAttribute("patient",patient);
		model.addAttribute("archiveFormDto",archiveFormDto);
		model.addAttribute("archive",archive);
		model.addAttribute("DL",Diseaselist);
		return "/ArchivePage/ArchiveUpdate";
	}

	// CLICK ARCHIVE UPDATE BUTTON (UPDATE)
	@PostMapping(value="/update/{id}")
	public String updatearchive(Model model,@PathVariable("id") Long arcid,
			@RequestParam("PostImgFile") List<MultipartFile> archiveImgFileList,
			@Valid ArchiveFormDto archiveFormDto, BindingResult bindingResult,
			@RequestParam("doctorname") String docname) {		
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "값을 가져오는 중 에러가 발생했습니다!");
			return "/archive/update/"+arcid;
		}
		try {
			archiveService.updateArchive(arcid, archiveFormDto, archiveImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "진료기록 수정 중 에러가 발생했습니다!");
			return "/archive/update/"+arcid;
		}
		
		Member patientinfo = memberService.findByMemberid(archiveFormDto.getId());
		List<Archive> AL = archiveService.returnArchive(patientinfo.getId());	
		model.addAttribute("patientinfo",patientinfo);
		model.addAttribute("ArchiveList",AL);
		return "redirect:/archive/search/"+archiveFormDto.getId();
	}
	
	
	// DELETE ARCHIVE 
	@GetMapping(value = "/delete/{id}")
	public String deletearchive(Model model, @PathVariable("id") Long arcid) {
		Archive archive = archiveRepository.getReferenceById(arcid);
		Member member = memberRepository.getReferenceById(archive.getMember().getId());
		String deleteStatus = archiveService.deleteArchive(arcid);
		
		model.addAttribute("errorMessage", deleteStatus);
		System.out.println("삭제 여부 : " + deleteStatus);
		return "redirect:/archive/search/"+member.getId();
	}
	
}
