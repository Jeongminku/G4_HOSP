package com.Tingle.G4hosp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.MedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MedService {
	
	private final MedRepository medRepository;

	public Long saveMed(MedFormDto medFormDto) throws Exception {

		// 진료과 등록
		Med med = medFormDto.createMed();
		medRepository.save(med);
		
		return med.getMedId();
	}

//========================================================================================

// 진료과 (Med) 리스트 불러오기
	public List<Med> getMedList() {
		return medRepository.findAll();
	}


}
