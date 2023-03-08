package com.Tingle.G4hosp.service;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.MedRepository;

import lombok.RequiredArgsConstructor;

// 진료과 서비스 담당
@Service
@RequiredArgsConstructor
@Transactional
public class MedService {
	
	private final MedRepository medRepository;
	private final MedRepository medRepository2;
	private final ChatRoomAccessService chatRoomAccessService;
	
	// 진료과 등록
	public Long saveMed(MedFormDto medFormDto) throws Exception {

		Med med = medFormDto.createMed();
		Med savedMed = medRepository.save(med);
		chatRoomAccessService.createChatRoomAccess(savedMed);
		
		return med.getMedId();
	}

//========================================================================================

	// 진료과 (Med) 리스트 불러오기
	public List<Med> getMedList() {
		return medRepository.findAll();
	}

	public List<Med> getMedListNotMyMed(Long medId) {
		return medRepository.getMedListNotMyMed(medId);
	}
	
	public Med findMedbyDocid(Long doctorid) {
		return medRepository.findMedbyDocid(doctorid);
	}
	
	public List<Med> getTesListNotMy(Long medId) {
		return medRepository2.getMedListNotMyMed(medId);
	}
	
	public Med findbyid(Long id) {
		return medRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	
	public void delmed(Long id) {
		Med med = medRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		medRepository.delete(med);
	}
}
