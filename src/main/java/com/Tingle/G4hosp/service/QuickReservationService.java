package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.QuickReservationDto;
import com.Tingle.G4hosp.entity.QuickReservation;
import com.Tingle.G4hosp.repository.QuickReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuickReservationService {

	private final QuickReservationRepository quickReservationRepository;
	
	public Long saveQR(QuickReservationDto quickReservationDto) {
		String hopetime;
		if(quickReservationDto.getRequesttime().equals("0830")) {
			hopetime = "0830~1200";
		}else
		{
			hopetime = "1300~1700";			
		}
		QuickReservation quickReservation = QuickReservation.createQR(quickReservationDto, hopetime);
		quickReservationRepository.save(quickReservation);	
		return quickReservation.getId();
	}
	
	public void updateQR() {
		String callyn = "Y";
		
	}
	
}
