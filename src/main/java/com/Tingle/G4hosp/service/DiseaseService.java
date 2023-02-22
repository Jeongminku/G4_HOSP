package com.Tingle.G4hosp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Tingle.G4hosp.dto.DiseaseFormDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.MedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

// 병명 서비스 담당

public class DiseaseService {

	private final MedRepository medRepository;
	private final DiseaseRepository diseaseRepository;

	// 병명 등록
	public Long saveDisease(DiseaseFormDto diseaseFormDto) throws Exception {

		Disease disease = diseaseFormDto.createDisease();
		diseaseRepository.save(disease);
		
		return disease.getDiseaseId();
	}
	
	public Disease createDisease(DiseaseFormDto diseaseFormDto) {
		Med med = medRepository.findById(diseaseFormDto.getDiseaseId()).orElseThrow(EntityNotFoundException::new);
		System.out.println("medtest : "+med);
		Disease disease = Disease.createDisease(med);
		
		return diseaseRepository.save(disease);
	}

//========================================================================================
	
	// 병명 (Disease) 리스트 불러오기
	public List<Disease> getDiseaseList() {
		return diseaseRepository.findAll();
	}
	
}
