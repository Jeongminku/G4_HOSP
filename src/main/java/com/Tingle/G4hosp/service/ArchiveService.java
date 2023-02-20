package com.Tingle.G4hosp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.ArchiveFormDto;
import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveService {
	
	private final MemberRepository memberRepository;
	
	
	
	public Long saveArchive(ArchiveFormDto archiveFormDto, List<MultipartFile> archiveImgFileList, Member mem) {
		
		Member member = memberRepository.findByLoginid(mem.getLoginid());
		
		
		return member.getId();
	}
	
	
}
