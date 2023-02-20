package com.Tingle.G4hosp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.ArchiveFormDto;
import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.ArchiveDisease;
import com.Tingle.G4hosp.entity.ArchiveImg;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.ArchiveDiseaseRepository;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveService {
	
	private final MemberRepository memberRepository;
	private final ArchiveRepository archiveRepository;
	private final DiseaseRepository diseaseRepository;
	private final ArchiveDiseaseRepository archiveDiseaseRepository;
	private final ArchiveImgService archiveImgService;
	
	
	public Long saveArchive(ArchiveFormDto archiveFormDto, List<MultipartFile> archiveImgFileList) throws Exception {
		
		// SELECT MEMBER OBJECT (FIND DOCTOR BY NAME - USING DTO's DOCTOR NAME)
		Member member = memberRepository.findbyNameindoctor(archiveFormDto.getDoctorname());
		
		// CREATE ARCHIVE BY MEMBER OBJECT & SAVE ARCHIVE
		Archive archive = Archive.createArchive(member, archiveFormDto);
		archiveRepository.save(archive);
		
		// CREATE ARCHIVE_DISEASE OBJECT BY ARCHIVE, DISEASE(USING ARCHIVEFORMDTO) OBJECT
		//  + SAVE ARCHIVE_DISEASE OBJECT
		Disease disease = diseaseRepository.findDiseasebyDiseasename(archiveFormDto.getDisease());
		if(disease == null) {
			
		}
		ArchiveDisease archiveDisease = ArchiveDisease.createAD(archive, disease, null);
		archiveDiseaseRepository.save(archiveDisease);
		
		// SAVE EACH IMGFILE FROM IMGFILELIST
		for(int i=0; i<archiveImgFileList.size(); i++) {
			ArchiveImg archiveImg = new ArchiveImg();
			archiveImg.setArchive(archive);
			archiveImgService.saveArchiveImg(archiveImg, archiveImgFileList.get(i));
		}
		return archive.getId();
	}
	
	
}
