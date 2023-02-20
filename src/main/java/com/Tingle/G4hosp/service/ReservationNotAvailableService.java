package com.Tingle.G4hosp.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.ReservationNotAvailable;
import com.Tingle.G4hosp.repository.ReservationNotAvailableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationNotAvailableService {
	private final ReservationNotAvailableRepository reservationNotAvailableRepository;
	
	public ReservationNotAvailable createReservationNotAvailable (Long doctorId, String[] selected) {
		for(String select : selected) {
			if(StringUtils.equals(select, ""));
		}
		return null;
	}
}
