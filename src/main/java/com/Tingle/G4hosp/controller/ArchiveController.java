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

import com.Tingle.G4hosp.dto.ArchiveFormDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.service.ArchiveService;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/archive")
@Controller
@RequiredArgsConstructor
public class ArchiveController {

	private final ArchiveService archiveService;
	
	// OPEN ARCHIVE PAGE
	@GetMapping(value="/")
	public String archiveview(Member member, Model model) {
		
		
		model.addAttribute("member", member);
		return "ArchivePage/ArchiveView";
	}
	
	// OPEN ARCHIVE WRITE PAGE
	@GetMapping(value="/write")
	public String archivewrite(Member member, Model model) {
		ArchiveFormDto archiveFormDto = new ArchiveFormDto();
		
		model.addAttribute("archiveFormDto",archiveFormDto);
		model.addAttribute("member", member);
		return "ArchivePage/ArchiveWrite";
	}
	
	// CLICK ARCHIVE WRITE BUTTON
	@PostMapping(value = "/write")
	public String writearchive(Member member, Model model,
			@RequestParam("PostImgFile") List<MultipartFile> archiveImgFileList,
			@Valid ArchiveFormDto archiveFormDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "ArchivePage/ArchiveWrite";
		}
		
		// SAVE ARCHIVE
		try {
			archiveService.saveArchive(archiveFormDto, archiveImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "진료기록 업로드 중 에러가 발생했습니다!");
			return "ArchivePage/ArchiveWrite";
		}
		return "ArchivePage/ArchiveView";
	}
	
}
