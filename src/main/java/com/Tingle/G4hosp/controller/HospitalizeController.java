package com.Tingle.G4hosp.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.constant.Hosp;
import com.Tingle.G4hosp.dto.ArchiveSearchDto;
import com.Tingle.G4hosp.dto.HospitalizeFormDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.service.ArchiveImgService;
import com.Tingle.G4hosp.service.ArchiveService;
import com.Tingle.G4hosp.service.DiseaseService;
import com.Tingle.G4hosp.service.HospitalizeService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/hospitalize")
@Controller
@RequiredArgsConstructor
public class HospitalizeController {

	private final MemberService memberService;
	private final HospitalizeService hospitalizeService;
	private final DiseaseService diseaseService;
	private final MedService medService;
	
	private final MedRepository medRepository;
	
	// OPEN HOSPITALIZE PAGE
	@GetMapping(value="/{id}")
	public String hospitalizewritepage(Model model, @PathVariable("id") Long patientid, Principal principal) {
		String loginId = principal.getName();
		Member doctor = memberService.findDocbyMid(loginId);
		List<Disease> disease = diseaseService.findDiseaseListByDocId(doctor.getLoginid());
		
		HospitalizeFormDto hospitalizeFormDto = new HospitalizeFormDto();
		Member patient = memberService.findByMemberid(patientid);
		
		// CHECK PATIENT IS HOSPITALIZED OR NOT
		Hospitalize hospitalize;
		String hospYN;
		if(hospitalizeService.FindHosbymemid(patientid) == null) {
			hospYN = "N";
		}else {
			hospitalize = hospitalizeService.FindHosbymemid(patientid);
			hospYN = hospitalize.getHospYN().toString();
		}
		model.addAttribute("hospYN",hospYN);
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patient.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);		
		
		hospitalizeFormDto.setDoctorname(doctor.getName());
		hospitalizeFormDto.setDoctormed(medService.findMedbyDocid(doctor.getId()).getMedName());
		
		model.addAttribute("disease",disease);
		model.addAttribute("hospitalizeFormDto",hospitalizeFormDto);
		model.addAttribute("patient",patient);
		
		return "hospitalizePage/hospitalize";
	}
	
	// CREATE HOSPITALIZE
	@PostMapping(value = "/write/{id}")
	public String createhospitalize(Model model, @PathVariable("id") Long patientid,
			@Valid HospitalizeFormDto hospitalizeFormDto) {		
		hospitalizeService.CreateHospitalize(hospitalizeFormDto, patientid);
		return "redirect:/archive/search/"+patientid;
	}
	
	// VIEW HOSPITALIZE STATUS
	@GetMapping(value = "/view/{id}")
	public String viewhospitalize(Model model, @PathVariable("id") Long patientid) {
		Member patient = memberService.findByMemberid(patientid);
		
		// CHECK PATIENT IS HOSPITALIZED OR NOT
		Hospitalize hospitalize;
		String hospYN;
		hospitalize = hospitalizeService.FindHosbymemid(patientid);
		hospYN = hospitalize.getHospYN().toString();
		model.addAttribute("hospYN",hospYN);

		// FIND DISEASE IF HOSPITALIZED BY DISEASE
		String disease;
		if(hospitalize.getHasdisease().equals(Hosp.Y)) {
			disease = diseaseService.findDiseasebyHospMemid(hospitalize.getMember().getId()).getDiseaseName();	
			Med med = medRepository.getReferenceById(diseaseService.findDiseasebyDiseasename(disease).getMed().getMedId());
			model.addAttribute("med",med);
		}else {
			disease = "질병없음";
			hospitalize.setDoctor("담당 의사 미지정");
		}
		
		// CALCULATE AGE BY MEMBER BIRTH
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String birth = patient.getBirth().substring(0, 4);
		int age = Integer.parseInt(now)-Integer.parseInt(birth);		
		model.addAttribute("age",age);
		
		
		
		model.addAttribute("hospitalize",hospitalize);
		model.addAttribute("patient",patient);
		model.addAttribute("disease",disease);			
		return "hospitalizePage/hospitalizeNow";
	}
	
	// DELETE HOSPITALIZE
	@GetMapping(value = "/delete/{id}")
	public String deletehospitalize(@PathVariable("id") Long patientid) {
		hospitalizeService.deleteHospitalize(patientid);
		return "redirect:/archive/search/"+patientid;
	}
	
}
