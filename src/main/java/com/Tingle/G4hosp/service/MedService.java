package com.Tingle.G4hosp.service;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.MedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedService {
	private final MedRepository medRepository;
	
	public Med createMed (MedFormDto medFormDto) {
		Med med = medFormDto.createMed();
		return medRepository.save(med);
	}
	
}
