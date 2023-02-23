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

@Service
@RequiredArgsConstructor
@Transactional

// 진료과 서비스 담당

public class MedService {
	
	private final MedRepository medRepository;

	// 진료과 등록
	public Long saveMed(MedFormDto medFormDto) throws Exception {

		Med med = medFormDto.createMed();
		medRepository.save(med);
		
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
	
	public Map<Long, String> findAllMedListToMap () {
		List<Med> medList = getMedList();
		Map<Long, String> medListMap = new HashMap<>();
		for(Med med : medList) {
			medListMap.put(med.getMedId(), med.getMedName());
		}
		return medListMap;
	}
	
}
