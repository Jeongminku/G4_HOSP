package com.Tingle.G4hosp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.dto.DiseaseFormDto;
import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.dto.MemberFormDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	// 관리자 페이지 화면
	@GetMapping(value="/adminpage")
	public String adminPage() {
		return "adminPage/adminPage";
		
	}
	
	// 진료과 입력 화면
	@GetMapping(value="/med")
	public String medForm(Model model) {
		model.addAttribute("medFormDto", new MedFormDto());
		return "adminPage/diseaseForm";
	}
	
	// 병명 입력 화면
	@GetMapping(value="/disease")
	public String diseaseForm(Model model) {
		model.addAttribute("diseaseFormDto", new DiseaseFormDto());
		return "adminPage/diseaseForm";
	}
	
	// 고객목록 페이지 화면
	@GetMapping(value="/patientList")
	public String memberList() {
		return "adminPage/patientList";
	}
	
	// 의사목록 페이지 화면
	@GetMapping(value="/doctorList")
	public String doctorList() {
		return "adminPage/doctorList";
	}
	
}
