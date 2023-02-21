package com.Tingle.G4hosp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.service.ArchiveService;
import com.Tingle.G4hosp.service.MemberImgService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/archive")
@Controller
@RequiredArgsConstructor
public class ArchiveController {
	private final MemberService memberService;
	private final ArchiveService archiveService;

	private final ArchiveRepository archiveRepository;
	private final MemberRepository memberRepository;
	
	// ARCHIVE LIST PAGE (READ)
	@GetMapping(value={"/","/{id}"})
	public String archiveview(Member member, Model model,@PathVariable("id") Optional<Long> patientid) {
		List<Archive> AL = new ArrayList<>();
		if(patientid.isPresent()) {
			AL = archiveService.returnArchive(patientid.get());
		}
		model.addAttribute("ArchiveList",AL);
		model.addAttribute("member", member);
		return "/ArchivePage/ArchiveView";
	}
	
	// ARCHIVE WRITE PAGE
	@GetMapping(value={"/write","/write{id}"})
	public String archivewrite(Model model,@PathVariable("id") Optional<Long> patientid) {
		ArchiveFormDto archiveFormDto = new ArchiveFormDto();
		Member patient = new Member();
		if(patientid.isPresent()) {
			patient = memberRepository.getReferenceById(patientid.get());			
		}
		model.addAttribute("archiveFormDto",archiveFormDto);
		model.addAttribute("patient", patient);
		return "/ArchivePage/ArchiveWrite";
	}
	
	// CLICK ARCHIVE WRITE BUTTON (CREATE)
	@PostMapping(value = {"/write","/write{id}"})
	public String writearchive(Model model,@PathVariable("id") Optional<Long> patientid,
			@RequestParam("PostImgFile") List<MultipartFile> archiveImgFileList,
			@Valid ArchiveFormDto archiveFormDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "값을 가져오는 중 에러가 발생했습니다!");
			return "/ArchivePage/ArchiveWrite";
		}
		// CREATE & SAVE ARCHIVE
		try {
			archiveService.saveArchive(archiveFormDto, archiveImgFileList, patientid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "진료기록 작성 중 에러가 발생했습니다!");
			return "/ArchivePage/ArchiveWrite";
		}
		return "/ArchivePage/ArchiveView";
	}
	
	// ARCHIVE UPDATE PAGE
	@GetMapping(value={"/update","/update/{id}"})
	public String archiveupdate(Member member, Model model,@PathVariable("id") Optional<Long> arcid) {
		ArchiveFormDto archiveFormDto = new ArchiveFormDto();		
		Archive archive = new Archive();
		if(arcid.isPresent()) {
			archive = archiveRepository.getReferenceById(arcid.get());
		}
		model.addAttribute("archiveFormDto",archiveFormDto);
		model.addAttribute("archive",archive);
		model.addAttribute("member", member);
		return "/ArchivePage/ArchiveUpdate";
	}
	
	// CLICK ARCHIVE UPDATE BUTTON (UPDATE)
	@PostMapping(value="/update/{id}")
	public String updatearchive(Model model,@PathVariable("id") Long arcid,
			@RequestParam("PostImgFile") List<MultipartFile> archiveImgFileList,
			@Valid ArchiveFormDto archiveFormDto, BindingResult bindingResult) {		
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "값을 가져오는 중 에러가 발생했습니다!");
			return "/archive/update/{id}";
		}
		try {
			archiveService.updateArchive(arcid, archiveFormDto, archiveImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "진료기록 수정 중 에러가 발생했습니다!");
			return "/archive/update/{id}";
		}
		return "/ArchivePage/ArchiveView";
	}
	
	
	// DELETE ARCHIVE 
	@GetMapping(value = "/delete/{id}")
	public String deletearchive(Model model, @PathVariable("id") Long arcid) {
		String deleteStatus = archiveService.deleteArchive(arcid);
		model.addAttribute("errorMessage", deleteStatus);
		return "/ArchivePage/ArchiveView";
	}
	
}
