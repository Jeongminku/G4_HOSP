package com.Tingle.G4hosp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.ArchiveFormDto;
import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.ArchiveImg;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveService {
	
	private final MemberRepository memberRepository;
	private final ArchiveImgService archiveImgService;
	
	
	public Long saveArchive(ArchiveFormDto archiveFormDto, List<MultipartFile> archiveImgFileList, Member mem) throws Exception {
		
		// SELECT MEMBER OBJECT (FIND DOCTOR BY NAME - USING DTO's DOCTOR NAME)
		Member member = memberRepository.findbyNameindoctor(archiveFormDto.getDoctorname());
		
		// CREATE ARCHIVE BY MEMBER OBJECT
		Archive archive = Archive.createArchive(member, archiveFormDto);
		
		// SAVE EACH IMGFILE FROM IMGFILELIST
		for(int i=0; i<archiveImgFileList.size(); i++) {
			ArchiveImg archiveImg = new ArchiveImg();
			archiveImg.setArchive(archive);
			archiveImgService.saveArchiveImg(archiveImg, archiveImgFileList.get(i));
		}
		return archive.getId();
	}
	
	
}
