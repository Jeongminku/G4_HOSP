package com.Tingle.G4hosp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.Tingle.G4hosp.repository.ArchiveImgRepository;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveService {
	
	private final MemberRepository memberRepository;
	private final ArchiveRepository archiveRepository;
	private final DiseaseRepository diseaseRepository;
	private final ArchiveDiseaseRepository archiveDiseaseRepository;
	private final ArchiveImgRepository archiveImgRepository;
	private final ArchiveImgService archiveImgService;
	
	public List<Archive> returnArchive(Long patientid) {
		List<Archive> archive = archiveRepository.findArchivelistbypatientid(patientid);
		return archive;
	}

	// CREATE & SAVE NEW ARCHIVE
	public Long saveArchive(ArchiveFormDto archiveFormDto, List<MultipartFile> archiveImgFileList,
			Optional<Long> patientid) throws Exception {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
 		
		// SELECT MEMBER OBJECT 
		// (FIND PATIENT BY OPTIONAL PATIENTID)
		// (FIND DOCTOR BY NAME - USING DTO's DOCTOR NAME)
		Member patientinfo = new Member();
		if(patientid.isPresent()) {
			patientinfo = memberRepository.getReferenceById(patientid.get());			
		}
		Member docinfo = memberRepository.findbyNameindoctor(archiveFormDto.getDoctorname());
		
		// CREATE ARCHIVE BY MEMBER OBJECT & SAVE ARCHIVE
		Archive archive = Archive.createArchive(docinfo, patientinfo, archiveFormDto, now);
		archiveRepository.save(archive);
		
		// CREATE ARCHIVE_DISEASE OBJECT BY ARCHIVE, DISEASE(USING ARCHIVEFORMDTO) OBJECT
		//  + SAVE ARCHIVE_DISEASE OBJECT
		Disease disease = new Disease();
		
		try {
			disease = diseaseRepository.findDiseasebyDiseasename(archiveFormDto.getDisease());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("archiveservice에서 disease 객체를 불러오는 동안 에러 발생!");
		}
		
		ArchiveDisease archiveDisease = ArchiveDisease.createAD(archive, disease);
		archiveDiseaseRepository.save(archiveDisease);
		
		// SAVE EACH IMGFILE FROM IMGFILELIST
		for(int i=0; i<archiveImgFileList.size(); i++) {
			ArchiveImg archiveImg = new ArchiveImg();
			archiveImg.setArchive(archive);
			archiveImgService.saveArchiveImg(archiveImg, archiveImgFileList.get(i));
		}
		return archive.getId();
	}
	
	// UPDATE ARCHIVE
	public Long updateArchive(Long arcid, ArchiveFormDto archiveFormDto, List<MultipartFile> archiveImgFileList
			) throws Exception {
		Member docinfo = memberRepository.findbyNameindoctor(archiveFormDto.getDoctorname());
		Archive archive = archiveRepository.getReferenceById(arcid);
		List<ArchiveImg> archiveImglist = archiveImgRepository.findbyARCid(arcid);
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		
		//UPDATE ARCHIVE & ARCHIVE IMG
		archive.updateArchive(docinfo, archiveFormDto, now);
		
		if(archiveImglist.size() == 0) {
			for(int i=0; i<archiveImgFileList.size(); i++) {
				ArchiveImg archiveImg = new ArchiveImg();
				archiveImg.setArchive(archive);
				archiveImgService.saveArchiveImg(archiveImg, archiveImgFileList.get(i));
			}
			return archive.getId();
		}
		try {
			for(int i=0; i<archiveImgFileList.size(); i++) {
				if(archiveImglist.get(i).getImgname().isEmpty()) {
					ArchiveImg archiveImg = new ArchiveImg();
					archiveImg.setArchive(archive);
					archiveImgService.saveArchiveImg(archiveImg, archiveImgFileList.get(i));
				}
				archiveImgService.updateArchiveImg(archiveImglist.get(i), archiveImglist.get(i).getId(), archiveImgFileList.get(i));
			}			
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return archive.getId();
		}
		return archive.getId();
	}
	
	// DELETE ARCHIVE
	public String deleteArchive(Long arcid) {
		Archive archive = archiveRepository.getReferenceById(arcid);
		try {
			ArchiveDisease AD = archiveDiseaseRepository.FindADbyARCid(arcid);
			archiveDiseaseRepository.delete(AD);
			List<ArchiveImg> archiveimglist = archiveImgRepository.findbyARCid(arcid);
			for(ArchiveImg ARI : archiveimglist) {
				archiveImgRepository.delete(ARI);
			}
			archiveRepository.delete(archive);
			return "삭제완료";
		} catch (Exception e) {
			return "삭제 작업간 에러가 발생했습니다";
		}
	}
	
	
}
